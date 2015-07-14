# O365-Android-Art-Curator
This sample demonstrates how to use the Outlook Mail API to get emails and attachments from Office 365. It's built for [iOS](https://github.com/OfficeDev/O365-iOS-ArtCurator), Android, [Web (Angular web app)](https://github.com/OfficeDev/O365-Angular-ArtCurator), and [Windows Phone](https://github.com/OfficeDev/O365-WinPhone-ArtCurator).
<br />
<br />
The Art Curator sample provides a different way to view your inbox. Imagine you own a company that sells artistic t-shirts. As the owner of the company, you receive lots of emails from artists with designs they want you to buy. Currently, you use your email client to open each message and attachment. Instead, you can use the Art Curator sample to give you an attachment-first view of your inbox so that you can pick and choose designs you like. 

This sample demonstrates the following operations from the Outlook Services Mail API: 
* [Get folders](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#GetFolders)
* [Get messages](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#Getmessages) (including filtering and using select) 
* [Get attachments](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#GetAttachments)
* [Update messages](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#Updatemessages)
* [Create and send messages](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#Sendmessages) (with and without an attachment) 

Prerequisites
==
* [Android Studio](https://developer.android.com/sdk/index.html) ver. 1.0+
* An Office 365 account. You can sign up for an [Office 365 Developer subscription](https://msdn.microsoft.com/en-us/library/office/fp179924.aspx) that includes the resources to start building Office 365 apps.

**Note**<br/>
You will also need to ensure your Azure subscription is bound to your Office 365 tenant. Check out the Active Directory team's blog post, [Creating and Managing Multiple Windows Azure Active Directories](http://blogs.technet.com/b/ad/archive/2013/11/08/creating-and-managing-multiple-windows-azure-active-directories.aspx) for instructions. In this post, the Adding a new directory section will explain how to do this. You can also read [Set up Azure Active Directory access for your Developer Site](https://msdn.microsoft.com/office/office365/howto/setup-development-environment#bk_CreateAzureSubscription) for more information.

Importing into Android Studio
==
* Clone this repository
* Open Android Studio
  * Import project (Eclipse ADT, Gradle, etc.) > select the target project's ```settings.gradle```

First start
==
This app contains pre-registered application information on Azure with **Send mail as a user** and **Read and write user mail** permissions.

App information is defined in ```com.microsoft.artcurator.conf.ServiceConstants```.
    
    public static final String CLIENT_ID = "1feaa784-0130-48d9-adeb-776fc65888c5";
    public static final String REDIRECT_URI = "https://useonlytoruntheartcuratorsample/";
        
For your own app,  [register your native client application on Azure](https://msdn.microsoft.com/library/azure/dn132599.aspx#BKMK_Adding). 

Specify the redirect URI when you register your application. Next, get the client id from the **CONFIGURE** page. 
The application *must* have the **Send mail as a user** and **Read and write user mail** permissions.

For more information, see [O365-Android-Connect sample](https://github.com/OfficeDev/O365-Android-Connect)
Limitations
==
* File support beyond ```.png``` and ```.jpg```
* Handling a single email with multiple attachments
* Paging (getting more than 50 emails)
* Handling folder name uniqueness

Questions and comments
==
* If you have any trouble running this sample, please [log and issue]()
* For all other questions and comments, send an email to [docthis@microsoft.com](mailto:docthis@microsoft.com)
* For general questions about the Office 365 APIs, post to [StackOverflow](http://stackoverflow.com/). Make sure that your questions or comments are tagged with [Office365] and [outlook-restapi]

Additional resources
==
* [Get started with Office 365 APIs in apps](https://msdn.microsoft.com/en-us/office/office365/howto/getting-started-Office-365-APIs)
* [Office Dev Center (Android)](http://dev.office.com/Android)
* [Office 365 APIs platform overview](http://stackoverflow.com/)

Copyright
==
Copyright (c) 2015 Microsoft. All rights reserved.
