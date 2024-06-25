# Documentation
## Sumary
- **Class**
  - [CredentialManager](./Documentation.md#class-credentialManager)
  - [CredentialEventHandler](./Documentation.md#class-credentialEventHandler)
- **Struct**
  - [CredentialUserData](./Documentation.md#struct-credentialUserData)
  - [CredentialExceptionData](./Documentation.md#credentialExceptionData)
  
## class CredentialManager
### Overview
Singleton pattern class, to communicate with kotlin libary and start the credential process.

This class will spawn a object that will be used to communicate with the libary, **dont delete the object**.
### Properties
```
static readonly UnityEvent<CredentialUserData> OnLoginSucess
```
This event will be triggered when a login is successful.
#

```
static readonly UnityEvent<CredentialExceptionData> OnLoginFailed
```
This event will be triggered when an exception during a credential process occurs.

# 
### Methods
```
static void SetupOathID(string oathID)
```
Call this method to setup the CredentialManager.
#

```
static void StartCredentialProcess()
```
Call this method to start the credential process. 

If sucess **OnLoginSucess** event will be triggered. 

If a excepetion occurs **OnLoginFailed** will be triggered.

## class CredentialEventHandler
### Overview
This class will be instantiate by the CredentialManager, and will receive messages from the kotlin libary. All messages are in json format.

## struct CredentialUserData
### Overview
Data struct that defines the user data.
### Properties
```
string displayName;
string familyName;
string givenName;
string id;
string phoneNumber;
string profilePictureUri;
```

## struct CredentialExceptionData
### Overview
Data struct that defines the excepetion data.
```
string type;
string message;
```
