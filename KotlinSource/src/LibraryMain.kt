package com.example.loginlibary

import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import java.security.MessageDigest
import java.util.UUID
import android.content.Context
import androidx.credentials.CredentialManagerCallback
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.unity3d.player.UnityPlayer
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
class UserData(
    val displayName:        String,
    val familyName:         String,
    val givenName:          String,
    val id:                 String,
    val phoneNumber:        String,
    val profilePictureUri:  String){

    companion object {
        @JvmStatic
        fun credentialToJson(googleIdTokenCredential :GoogleIdTokenCredential): String{
            val userData = UserData(
                googleIdTokenCredential.displayName.toString(),
                googleIdTokenCredential.familyName.toString(),
                googleIdTokenCredential.givenName.toString(),
                googleIdTokenCredential.id,
                googleIdTokenCredential.phoneNumber.toString(),
                googleIdTokenCredential.profilePictureUri.toString()
            )
            return Json.encodeToString(userData)
        }
    }
}

@Serializable
class ExceptionCredential{
    lateinit var type: String
    lateinit var exceptionMessage: String
}

class UnitCallbackCredential : CredentialManagerCallback<GetCredentialResponse, GetCredentialException>{

    private lateinit var objectName: String
    private lateinit var methodName: String
    private lateinit var exceptionName: String

    fun setup(objectName: String, methodName: String, exceptionName: String){
        this.objectName = objectName
        this.methodName = methodName
        this.exceptionName = exceptionName
    }

    override fun onResult(result: GetCredentialResponse) {
        val googleIdTokenCredential = GoogleIdTokenCredential
            .createFrom(result.credential.data)

        UnityPlayer.UnitySendMessage(objectName, methodName, UserData.credentialToJson(googleIdTokenCredential));
    }

    override fun onError(e: GetCredentialException) {
        val exceptionData = ExceptionCredential()
        exceptionData.type = e.type
        exceptionData.exceptionMessage = e.message.toString()

        UnityPlayer.UnitySendMessage(objectName, exceptionName, Json.encodeToString(exceptionData));
    }
}

class LibraryMain {

    fun getUserDataUnity(oathClientID: String, objectName: String, methodName: String, exceptionName: String)
    {
        val unitCallbackCredential = UnitCallbackCredential()
        unitCallbackCredential.setup(objectName, methodName, exceptionName)
        getUserData(UnityPlayer.currentActivity, oathClientID, unitCallbackCredential)
    }

    fun getUserData(context: Context,
                    oathClientID: String,
                    callback: CredentialManagerCallback<GetCredentialResponse, GetCredentialException>){

        val credentialManger = CredentialManager.create(context)
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(oathClientID)
            .setNonce(getNonce())
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        credentialManger.getCredentialAsync(
            request = request,
            context = context,
            cancellationSignal = null,
            executor = context.mainExecutor,
            callback = callback
        )
    }

    private fun getNonce(): String{
        val bytes = UUID.randomUUID().toString().toByteArray()
        val digest = MessageDigest.getInstance("SHA-256").digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}