# Manual
## Sumary
- [How this Works?](./MANUAL.md#how-this-works?)
- [Setting up](./MANUAL.md#setting-up)

## How this Works?
Google Credential Manager libraries are acessable in the Android layer of the application, and Unity let you interact with this layer using [JNI](https://docs.unity3d.com/ScriptReference/AndroidJNI.html), or using the [higher-level API](https://docs.unity3d.com/Manual/android-plugins-java-code-from-c-sharp.html).

For simplicity, I choose to use the high-level API, and create a kotlin library to call the Credential Manager API at the OS level. This repository has the kotlin library [source](https://github.com/gabriel01913/UnityGoogleCredentials/tree/main/KotlinSource), so you can build or change it if you want.

The compiled library is included in the package. The only purpose for this custom libary is to bridge the communication bettwen the Android layer and you C# code layer. The libary use the UnityPlayerActivity API to send a message to a object in the scene, and this object will trigger a event so you can listen to the event.

## Setting up
After you install the Unity Editor 6.0 or higher, you need to add the modules to build to Android and IOS. 

The IOS is a requirement for the EDM4U (External Dependency Manager for Unity).

This package will download and add the Google dependencies that we need to the project. The EDM4U will use the MyDependencies.xml (include in the package), to know what is the libraries that our project needs.

After you download and install the EDM4U in your project, open the menu:
- "Assets>External Dependency Manager>Android Resolver>Settings"
- Under the "Use Full Custom Local Maven Repo Path" section, enable the option "When building Android app through Unity".
- Click Ok.

Now import the released package of the pluggin.
