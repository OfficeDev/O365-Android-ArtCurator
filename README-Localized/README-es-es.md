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
[![Estado de la compilación](https://travis-ci.org/OfficeDev/O365-Android-ArtCurator.svg?branch=master)](https://travis-ci.org/OfficeDev/O365-Android-ArtCurator)


Este ejemplo muestra cómo usar la API del Correo de Outlook para obtener correos electrónicos y datos adjuntos de Office 365. Se creó para iOS, [iOS](https://github.com/OfficeDev/O365-iOS-ArtCurator), Android, [Web (aplicación web Angular)](https://github.com/OfficeDev/O365-Angular-ArtCurator) y [Windows Phone](https://github.com/OfficeDev/O365-WinPhone-ArtCurator). Consulte nuestro [artículo sobre Medium](https://medium.com/@iambmelt/14296d0a25be).
<br />
<br />
El ejemplo de Art Curator proporciona una forma diferente de ver su bandeja de entrada. Imagine que posee una empresa que vende camisetas artísticas. Como propietario de la empresa, recibe muchos mensajes de correo electrónico de artistas con diseños que desean que compre. Actualmente, usa el cliente de correo electrónico para abrir cada mensaje y datos adjuntos. En su lugar, puede usar el ejemplo de Art Curator para proporcionarle una primera vista de los datos adjuntos de su bandeja de entrada para que pueda elegir y seleccionar los diseños que le gusten. 

Este ejemplo muestra las siguientes operaciones de la API de correo de Outlook:
* [Obtener carpetas](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#GetFolders)
* [Recibir mensajes](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#Getmessages) (incluyendo los filtrados y el uso de selección)
* [Obtener datos adjuntos](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#GetAttachments)
*[Actualizar mensajes](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#Updatemessages)
* [Crear y enviar mensajes](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#Sendmessages) (con y sin datos adjuntos). 

[![Android Art Curator para Office 365 ](/readme-images/artcurator_android.png)![haga clic para ver el funcionamiento de la muestra](/readme-images/artcurator_android.png)

Requisitos previos
==
* [Android Studio](https://developer.android.com/sdk/index.html) ver. 1.0+
* Una cuenta de Office 365. Puede registrarse en una [suscripción a Office 365 Developer](https://msdn.microsoft.com/en-us/library/office/fp179924.aspx) que incluya los recursos para comenzar a crear aplicaciones de Office 365.

**Nota**<br/>
También necesitará asegurarse de que su suscripción a Azure esté enlazada a su inquilino de Office 365. Consulte la publicación del blog del equipo de Active Directory, [Crear y administrar varios directorios de Windows Azure Active](http://blogs.technet.com/b/ad/archive/2013/11/08/creating-and-managing-multiple-windows-azure-active-directories.aspx) para obtener instrucciones. En esta publicación, la sección Agregar un nuevo directorio le explicará cómo hacerlo. También puede leer [Configurar el acceso a Azure Active Directory para su sitio para desarrolladores](https://msdn.microsoft.com/office/office365/howto/setup-development-environment#bk_CreateAzureSubscription) para obtener más información.

Importar en Android Studio
==
* Clone este repositorio.
* Abrir Android Studio
  * Importar proyecto (Eclipse ADT, Gradle, etc.) > seleccionar la configuración de ```del proyecto de destino. Gradle```

Primer inicio
==
Esta aplicación contiene información de la aplicación registrada previamente en Azure con **enviar correo como usuario** y **leer y escribir el correo del usuario** permisos.

La información de la aplicación está definida en ```com.microsoft.artcurator.conf.ServiceConstants```.
    
    public static final String CLIENT_ID = "1feaa784-0130-48d9-adeb-776fc65888c5";
    public static final String REDIRECT_URI = "https://useonlytoruntheartcuratorsample/";
        
Para su desarrollo, [debe registrar su aplicación de cliente nativo en Azure](https://msdn.microsoft.com/library/azure/dn132599.aspx#BKMK_Adding). 

Especifique el identificador URI de redireccionamiento al registrar la aplicación.
A continuación, obtenga el Id. de cliente desde la página **CONFIGURAR**. La aplicación *debe* tener los permisos de **Enviar correo como un usuario** y **Leer y escribir correo de usuario**.

Para obtener más información, vea [O365-Android-conectar ejemplo](https://github.com/OfficeDev/O365-Android-Connect)
las limitaciones
==
* Compatibilidad de archivos más allá de ```.png``` y ```.jpg```
* Controlar un solo correo electrónico con varios datos adjuntos
* Paginación (obtener más de 50 correos electrónicos)
* Controlar la unicidad del nombre de carpeta
* La carpeta de envío debe ser una carpeta de nivel superior

Preguntas y comentarios
==
* Si tiene algún problema para ejecutar este ejemplo, [registro y problema](https://github.com/OfficeDev/O365-Android-ArtCurator/issues).
* Para realizar preguntas generales acerca de las API de Office 365, publíquelas en [Stack Overflow](http://stackoverflow.com/). Asegúrese de que sus preguntas o comentarios se etiquetan con \[Office365] y \[outlook-restapi].

Recursos adicionales
==
* [Introducción a las API de Office 365 en aplicaciones](https://msdn.microsoft.com/en-us/office/office365/howto/getting-started-Office-365-APIs)
* [Centro de desarrollo de Office (Android)](http://dev.office.com/Android)
* [Información general sobre la plataforma de las API de Office 365](http://stackoverflow.com/)
* [Art Curator para iOS](https://github.com/OfficeDev/O365-iOS-ArtCurator)
* [Art Curator para Windows Phone](https://github.com/OfficeDev/O365-WinPhone-ArtCurator)
* [Art Curator para la Web (aplicación web Angular)](https://github.com/OfficeDev/O365-Angular-ArtCurator)

Derechos de autor
==
Copyright (c) 2015 Microsoft. Todos los derechos reservados.


Este proyecto ha adoptado el [Código de conducta de código abierto de Microsoft](https://opensource.microsoft.com/codeofconduct/). Para obtener más información, vea [Preguntas frecuentes sobre el código de conducta](https://opensource.microsoft.com/codeofconduct/faq/) o póngase en contacto con [opencode@microsoft.com](mailto:opencode@microsoft.com) si tiene otras preguntas o comentarios.
