---
page_type: sample
products:
- office-outlook
- office-365
languages:
- java
extensions:
  contentType: samples
  platforms:
  - Android
  createdDate: 6/26/2015 3:03:39 PM
---
# O365-Android-Art-Curator
[![État de création](https://travis-ci.org/OfficeDev/O365-Android-ArtCurator.svg?branch=master)](https://travis-ci.org/OfficeDev/O365-Android-ArtCurator)


Cet exemple indique comment utiliser l’API de messagerie Outlook pour obtenir des messages électroniques et des pièces jointes à partir d’Office 365. Il est conçu pour [iOS](https://github.com/OfficeDev/O365-iOS-ArtCurator), Android, [web (application web Angular)](https://github.com/OfficeDev/O365-Angular-ArtCurator) et [Windows Phone](https://github.com/OfficeDev/O365-WinPhone-ArtCurator). Consultez notre [article sur les moyennes](https://medium.com/@iambmelt/14296d0a25be).
<br />
<br />
L’exemple Art Curator fournit une autre façon d’afficher votre boîte de réception. Imaginez que vous êtes propriétaire d’une entreprise qui vend des tee-shirts artistiques. En tant que propriétaire de l’entreprise, vous recevez de nombreux messages électroniques de la part d’artistes comportant des conceptions qu’ils tentent de vous vendre. Vous utilisez actuellement votre client de messagerie pour ouvrir chaque message et pièce jointe. Au lieu de cela, vous pouvez utiliser l’exemple Art Curator pour obtenir un premier aperçu des pièces jointes de votre boîte de réception afin que vous puissiez sélectionner et choisir les conceptions qui vous plaisent. 

Cet exemple illustre les opérations suivantes de l’API de courrier Outlook :
* [Obtenir des dossiers](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#GetFolders)
* [Obtenir des messages](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#Getmessages) (notamment le filtrage et l’utilisation de la fonction select)
* [Obtenir des pièces jointes](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#GetAttachments)
* [Messages de mise à jour](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#Updatemessages)
* [Créer et envoyer des messages](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#Sendmessages) (avec et sans pièce jointe) 

[![Android Art Curator Office 365](/readme-images/artcurator_android.png)![Cliquez ici pour voir l’exemple en action](/readme-images/artcurator_android.png)

Conditions préalables
==
* [Android Studio](https://developer.android.com/sdk/index.html) ver. 1.0 +
* Un compte Office 365. Vous pouvez vous inscrire à [Office 365 Developer](https://msdn.microsoft.com/en-us/library/office/fp179924.aspx) pour accéder aux ressources permettant de commencer à créer des applications Office 365.

**Note**<br/>
Vous devrez également vous assurer que votre abonnement Azure est lié à votre client Office 365. Consultez le billet du blog de l’équipe d’Active Directory relatif à la [création et la gestion de plusieurs fenêtres dans les répertoires Windows Azure Active Directory](http://blogs.technet.com/b/ad/archive/2013/11/08/creating-and-managing-multiple-windows-azure-active-directories.aspx) pour obtenir des instructions. Dans ce billet de blog, la section sur l’ajout d’un nouveau répertoire vous explique comment procéder. Vous pouvez également lire l’article relatif à la [configuration de l’accès Azure Active Directory pour votre site de développeur](https://msdn.microsoft.com/office/office365/howto/setup-development-environment#bk_CreateAzureSubscription) pour plus d’informations.

Importation vers Android Studio
==
* Cloner ce référentiel
* Ouvrir Android Studio
  * Importez un projet (Eclipse ADT, Gradle, etc.) > sélectionnez l’élément ```settings.gradle```

Premier démarrage
==
Cette application contient des informations d’application pré-enregistrées sur Azure avec des autorisations **Envoyer un courrier électronique en tant qu’utilisateur** et **Lire et écrire un courrier électronique d’utilisateur**.

Les informations relatives à l’application sont définies dans ```com.microsoft.artcurator.conf.ServiceConstants```.
    
    public static final String CLIENT_ID = "1feaa784-0130-48d9-adeb-776fc65888c5";
    public static final String REDIRECT_URI = "https://useonlytoruntheartcuratorsample/";
        
Pour votre propre application, [inscrivez votre application cliente native sur Azure](https://msdn.microsoft.com/library/azure/dn132599.aspx#BKMK_Adding). 

Indiquez l’URI de redirection lorsque vous inscrivez votre application.
Ensuite, obtenez l’ID client à partir de la page **CONFIGURER**. L’application *doit* disposer des autorisations **Envoyer un courrier électronique en tant qu’utilisateur** et **Lire et écrire un courrier électronique d’utilisateur**.

Pour plus d’informations, voir Exemples de limitations [Office 365-Android-Connect](https://github.com/OfficeDev/O365-Android-Connect)
==
* Prise en charge de formats de fichiers autres que ```.png``` et ```.jpg```
* Gestion d’un courrier électronique unique avec plusieurs pièces jointes
* Pagination (réception de plus de 50 courriers électroniques)
* Gestion de l’unicité des noms de dossiers
* Dossier de soumission devant être un dossier de niveau supérieur

Questions et commentaires
==
* Si vous rencontrez des difficultés pour exécuter cet exemple, veuillez [consigner un problème](https://github.com/OfficeDev/O365-Android-ArtCurator/issues).
* Pour des questions générales relatives aux API Office 365, publiez sur [Stack Overflow](http://stackoverflow.com/). Veillez à poser vos questions ou à rédiger vos commentaires avec les tags \[Office365] et \[outlook-restapi].

Ressources supplémentaires
==
* [Prise en main des API Office 365 dans les applications](https://msdn.microsoft.com/en-us/office/office365/howto/getting-started-Office-365-APIs)
* [Centre de développement Office (Android)](http://dev.office.com/Android)
* [Présentation de la plateforme des API Office 365](http://stackoverflow.com/)
* [Art Curator pour iOS](https://github.com/OfficeDev/O365-iOS-ArtCurator)
* [Art Curator pour Windows Phone](https://github.com/OfficeDev/O365-WinPhone-ArtCurator)
* [Art Curator pour le web (application web angulaire)](https://github.com/OfficeDev/O365-Angular-ArtCurator)

Copyright
==
Copyright (c) 2015 Microsoft. Tous droits réservés.


Ce projet a adopté le [code de conduite Open Source de Microsoft](https://opensource.microsoft.com/codeofconduct/). Pour en savoir plus, reportez-vous à la [FAQ relative au code de conduite](https://opensource.microsoft.com/codeofconduct/faq/) ou contactez [opencode@microsoft.com](mailto:opencode@microsoft.com) pour toute question ou tout commentaire.
