# Manual
## Sumary
- [How this Works?](./MANUAL.md#how-this-works?)
- [Setting up](./MANUAL.md#setting-up)
- [Credential Manger API](./MANUAL.md#credential-manager-api)
- [Google Cloud Oath authentication](./MANUAL.md#google-cloud-oath-authentication)

## How this Works?
Google Credential Manager libraries are acessable in the Android layer of the application, and Unity let you interact with this layer using [JNI](https://docs.unity3d.com/ScriptReference/AndroidJNI.html), or using the [higher-level API](https://docs.unity3d.com/Manual/android-plugins-java-code-from-c-sharp.html).

For simplicity, I choose to use the high-level API, and create a kotlin library to call the Credential Manager API at the OS level. This repository has the kotlin library [source](https://github.com/gabriel01913/UnityGoogleCredentials/tree/main/KotlinSource), so you can build or change it if you want.

The compiled library is included in the package. The only purpose for this custom library is to bridge the communication between the Android layer and you C# code layer. The library uses the UnityPlayerActivity API to send a message to an object in the scene, and this object will trigger an event so you can listen to the event.

## Setting up
After you install the Unity Editor 6.0 or higher, you need to add the modules to build to Android and IOS. 

The IOS is a requirement for the EDM4U (External Dependency Manager for Unity).

The EDM4U plug-in will download and add the Google dependencies that we need to the project. 

The EDM4U will use the MyDependencies.xml (include in the package), to know what is the libraries that our project needs.

After you download and install the EDM4U in your project, open the menu:
- Assets>External Dependency Manager>Android Resolver>Settings
- Under the section **Use Full Custom Local Maven Repo Path** , enable the option **When building Android app through Unity**.
- Click Ok.
  
If a popup ask to **Enable auto-resolution** click **Ok**.
Now import the released package of this project.

## Credential Manager API
The class CredentialManager it's a singleton pattern object that will interact with the kotlin library and call the Google Credential Manager API.

You need to setup the object by calling **CredentialManager.SetupOathID([your ID])** and then calling **CredentialManager.StartCredentialProcess()** to start the process.

**CredentialManager.OnLoginSucess** and **CredentialManager.OnLoginFailed** are UnityEvents that you should listen to, both pass structs that represent the user data and the excepetion messages.

You can check the **SampleUI.cs** to see a example on how to use the class. A sample scene are included so you can test.

For undurstand more aboute the Credential Manager API, check the [documentation](Documentation.md).

## Google Cloud Oath authentication
To use the Google Credential Manager, you have to setup the Google Cloud Oauth authentication.

In the Google Cloud Console open **API & Services**.

Go to **Credentials** and click in **Create Credentials>Oauth client ID**.

You shoud create two clients, one of type **Android** and one of type **Web Application**. The **Android** will be for Google validade your application, and the **Web Application** will be used to call the Google Credential Manager.

When calling setup the CredentialManager.SetupOathID method, pass the client ID of the **Web Application**.
