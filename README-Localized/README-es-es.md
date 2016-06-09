# O365-Android-Art-Curator
[![Estado de la compilación](https://travis-ci.org/OfficeDev/O365-Android-ArtCurator.svg?branch=master)](https://travis-ci.org/OfficeDev/O365-Android-ArtCurator)


Este ejemplo muestra cómo usar la API de Correo de Outlook para obtener correos electrónicos y datos adjuntos de Office 365. Se ha creado para [iOS](https://github.com/OfficeDev/O365-iOS-ArtCurator), Android, [Web (aplicación web Angular)](https://github.com/OfficeDev/O365-Angular-ArtCurator) y [Windows Phone](https://github.com/OfficeDev/O365-WinPhone-ArtCurator). Consulte nuestro [artículo en Medium](https://medium.com/@iambmelt/14296d0a25be).
<br />
<br />
El ejemplo de Art Curator proporciona una forma diferente de ver la bandeja de entrada. Imagine que posee una empresa que vende camisetas artísticas. Como propietario de la empresa, recibe muchos mensajes de correo electrónico de artistas con diseños que desean que compre. Actualmente, usa el cliente de correo electrónico para abrir cada mensaje y datos adjuntos. En su lugar, puede usar el ejemplo de Art Curator para proporcionarle una primera vista de los datos adjuntos de la bandeja de entrada para que puede elegir y seleccionar los diseños que le gusten. 

Este ejemplo muestra las siguientes operaciones de la API de correo de los servicios de Outlook: 
* [Obtener carpetas](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#GetFolders)
* [Obtener mensajes](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#Getmessages) (incluyendo la selección de filtrado y uso) 
* [Obtener datos adjuntos](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#GetAttachments)
* [Actualizar mensajes](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#Updatemessages)
* [Crear y enviar mensajes](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#Sendmessages) (con y sin datos adjuntos) 

[![Office 365 Android Art Curator](../readme-images/artcurator_android.png)](https://youtu.be/4LOvkweDfhY "Haga clic para ver el ejemplo en funcionamiento")

Requisitos previos
==
* [Android Studio](https://developer.android.com/sdk/index.html) ver. 1.0+
* Una cuenta de Office 365. Puede registrarse en una [suscripción a Office 365 Developer](https://msdn.microsoft.com/es-es/library/office/fp179924.aspx) que incluya los recursos para comenzar a crear aplicaciones de Office 365.

**Nota**<br/>
También necesitará asegurarse de que su suscripción a Azure esté enlazada a su inquilino de Office 365. Consulte la publicación del blog del equipo de Active Directory, [Crear y administrar varios directorios de Windows Azure Active Directory](http://blogs.technet.com/b/ad/archive/2013/11/08/creating-and-managing-multiple-windows-azure-active-directories.aspx) para obtener instrucciones. En esta publicación, la sección Agregar un nuevo directorio le explicará cómo hacerlo. También puede leer [Configurar el acceso a Azure Active Directory para su Sitio para desarrolladores](https://msdn.microsoft.com/office/office365/howto/setup-development-environment#bk_CreateAzureSubscription) para obtener más información.

Importar en Android Studio
==
* Clonar este repositorio
* Abrir Android Studio
  * Importar proyecto (Eclipse ADT, Gradle, etc.) > seleccionar el proyecto de destino ```settings.gradle```

Primer inicio
==
Esta aplicación contiene información de la aplicación registrada previamente en Azure con los permisos **Enviar correo como un usuario** y **Leer y escribir correo de usuario**.

La información de la aplicación está definida en ```com.microsoft.artcurator.conf.ServiceConstants```.
    
    public static final String CLIENT_ID = "1feaa784-0130-48d9-adeb-776fc65888c5";
    public static final String REDIRECT_URI = "https://useonlytoruntheartcuratorsample/";
        
Para su propia aplicación,  [registre su aplicación de cliente nativo en Azure](https://msdn.microsoft.com/library/azure/dn132599.aspx#BKMK_Adding). 

Especifique el identificador URI de redireccionamiento al registrar la aplicación. A continuación, obtenga el identificador de cliente de la página **CONFIGURAR**. 
La aplicación *debe* tener los permisos **Enviar correo como un usuario** y **Leer y escribir correo de usuario**.

Para obtener más información, consulte [ejemplo Connect de Office 365 para Android](https://github.com/OfficeDev/O365-Android-Connect)
Limitaciones
==
* Compatibilidad de archivos además de ```.png``` y ```.jpg```
* Controlar un solo correo electrónico con varios datos adjuntos
* Paginación (obtener más de 50 correos electrónicos)
* Controlar la unicidad del nombre de carpeta
* La carpeta de envío debe ser una carpeta de nivel superior

Preguntas y comentarios
==
* Si tiene algún problema al ejecutar este ejemplo, [regístrelo](https://github.com/OfficeDev/O365-Android-ArtCurator/issues).
* Para realizar preguntas generales acerca de las API de Office 365, publíquelas en [Stack Overflow](http://stackoverflow.com/). Asegúrese de que sus preguntas o comentarios se etiquetan con [Office365] y [outlook-restapi]

Recursos adicionales
==
* [Introducción a las API de Office 365 en aplicaciones](https://msdn.microsoft.com/en-us/office/office365/howto/getting-started-Office-365-APIs)
* [Centro de desarrollo de Office (Android)](http://dev.office.com/Android)
* [Información general de la plataforma de las API de Office 365](http://stackoverflow.com/)
* [Art Curator para iOS](https://github.com/OfficeDev/O365-iOS-ArtCurator)
* [Art Curator para Windows Phone](https://github.com/OfficeDev/O365-WinPhone-ArtCurator)
* [Art Curator para Web (aplicación web Angular)](https://github.com/OfficeDev/O365-Angular-ArtCurator)

Copyright
==
Copyright (c) 2015 Microsoft. Todos los derechos reservados.

