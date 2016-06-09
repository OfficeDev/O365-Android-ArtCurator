# O365-Android-Art-Curator
[![État de création](https://travis-ci.org/OfficeDev/O365-Android-ArtCurator.svg?branch=master)](https://travis-ci.org/OfficeDev/O365-Android-ArtCurator)


Cet exemple présente comment utiliser l’API de messagerie Outlook pour obtenir des messages électroniques et des pièces jointes à partir d’Office 365. Il est conçu pour [iOS](https://github.com/OfficeDev/O365-iOS-ArtCurator), Android, [Web (application web Angular)](https://github.com/OfficeDev/O365-Angular-ArtCurator) et [Windows Phone](https://github.com/OfficeDev/O365-WinPhone-ArtCurator). Consultez notre [article sur Medium](https://medium.com/@iambmelt/14296d0a25be).
<br />
<br />
L’exemple Art Curator fournit une autre façon d’afficher votre boîte de réception. Imaginez que vous êtes propriétaire d’une entreprise qui vend des tee-shirts artistiques. En tant que propriétaire de l’entreprise, vous recevez de nombreux messages électroniques de la part d’artistes comportant des conceptions qu’ils tentent de vous vendre. Vous utilisez actuellement votre client de messagerie pour ouvrir chaque message et pièce jointe. Au lieu de cela, vous pouvez utiliser l’exemple Art Curator pour obtenir un premier aperçu des pièces jointes de votre boîte de réception afin que vous puissiez sélectionner et choisir les conceptions qui vous plaisent. 

Cet exemple illustre les opérations suivantes réalisées à partir de l’API de services de messagerie Outlook : 
* [Obtenir des dossiers](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#GetFolders)
* [Obtenir des messages](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#Getmessages) (y compris le filtrage et la sélection) 
* [Obtenir des pièces jointes](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#GetAttachments)
* [Mettre à jour des messages](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#Updatemessages)
* [Créer et envoyer des messages](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#Sendmessages) (avec et sans pièce jointe) 

[![Office 365 Android Art Curator](../readme-images/artcurator_android.png)](https://youtu.be/4LOvkweDfhY "Cliquez ici pour voir l’exemple en action")

Conditions requises
==
* [Android Studio](https://developer.android.com/sdk/index.html), version 1.0 +
* Un compte Office 365. Vous pouvez vous inscrire à [Office 365 Developer](https://msdn.microsoft.com/en-us/library/office/fp179924.aspx) pour accéder aux ressources permettant de commencer à créer des applications Office 365.

**Remarque**<br/>
Vous devrez également vous assurer que votre abonnement Azure est lié à votre client Office 365. Consultez le billet de blog de l’équipe Active Directory relatif à la [création et à la gestion de plusieurs répertoires Windows Azure Active Directory](http://blogs.technet.com/b/ad/archive/2013/11/08/creating-and-managing-multiple-windows-azure-active-directories.aspx) pour obtenir des instructions. Dans ce billet de blog, la section sur l’ajout d’un nouveau répertoire vous explique comment procéder. Vous pouvez également lire l’article relatif à la [configuration d’un accès Azure Active Directory pour votre site du développeur](https://msdn.microsoft.com/office/office365/howto/setup-development-environment#bk_CreateAzureSubscription) pour plus d’informations.

Importation vers Android Studio
==
* Cloner ce référentiel
* Ouvrir Android Studio
  * Importez un projet (Eclipse ADT, Gradle, etc.) > Sélectionnez l’élément ```settings.gradle``` cible du projet

Premier démarrage
==
Cette application contient des informations d’application pré-enregistrées sur Azure avec des autorisations **Envoyer un courrier électronique en tant qu’utilisateur** et **Lire et écrire un courrier électronique d’utilisateur**.

Les informations relatives à l’application sont définies dans ```com.microsoft.artcurator.conf.ServiceConstants```.
    
    public static final String CLIENT_ID = "1feaa784-0130-48d9-adeb-776fc65888c5";
    public static final String REDIRECT_URI = "https://useonlytoruntheartcuratorsample/";
        
Pour votre propre application, [inscrivez votre application cliente native auprès d’Azure](https://msdn.microsoft.com/library/azure/dn132599.aspx#BKMK_Adding). 

Indiquez l’URI de redirection lorsque vous inscrivez votre application. Ensuite, obtenez l’ID client à partir de la page **CONFIGURER**. 
L’application *doit* disposer des autorisations **Envoyer un courrier électronique en tant qu’utilisateur** et **Lire et écrire un courrier électronique d’utilisateur**.

Pour plus d’informations, voir la page présentant un [exemple O365-Android-Connect](https://github.com/OfficeDev/O365-Android-Connect).
Limitations
==
* Prise en charge de formats de fichiers autres que ```.png``` et ```.jpg```
* Gestion d’un courrier électronique unique avec plusieurs pièces jointes
* Pagination (réception de plus de 50 courriers électroniques)
* Gestion de l’unicité des noms de dossiers
* Dossier de soumission devant être un dossier de niveau supérieur

Questions et commentaires
==
* Si vous rencontrez des problèmes lors de l’exécution de cet exemple, veuillez [les consigner](https://github.com/OfficeDev/O365-Android-ArtCurator/issues).
* Pour des questions générales relatives aux API Office 365, publiez sur [Stack Overflow](http://stackoverflow.com/). Posez vos questions avec les balises [Office365] et [outlook-restapi].

Ressources supplémentaires
==
* [Prise en main d’API Office 365 dans les applications](https://msdn.microsoft.com/fr-fr/office/office365/howto/getting-started-Office-365-APIs)
* [Centre de développement Office (Android)](http://dev.office.com/Android)
* [Présentation de la plateforme des API Office 365](http://stackoverflow.com/)
* [Art Curator pour iOS](https://github.com/OfficeDev/O365-iOS-ArtCurator)
* [Art Curator pour Windows Phone](https://github.com/OfficeDev/O365-WinPhone-ArtCurator)
* [Art Curator pour Web (application web Angular)](https://github.com/OfficeDev/O365-Angular-ArtCurator)

Copyright
==
Copyright (c) 2015 Microsoft. Tous droits réservés.

