# O365-Android-Art-Curator
[![Состояние сборки](https://travis-ci.org/OfficeDev/O365-Android-ArtCurator.svg?branch=master)](https://travis-ci.org/OfficeDev/O365-Android-ArtCurator)


В этом примере показано, как извлекать сообщения и вложения из Office 365 с помощью API Почты Outlook. Это приложение создано для [iOS](https://github.com/OfficeDev/O365-iOS-ArtCurator), Android, [веб-браузеров (веб-приложение на платформе Angular](https://github.com/OfficeDev/O365-Angular-ArtCurator) и [Windows Phone](https://github.com/OfficeDev/O365-WinPhone-ArtCurator). Просмотрите нашу [статью на сайте Medium](https://medium.com/@iambmelt/14296d0a25be).
<br />
<br />
Art Curator воплощает новый подход к просмотру папки "Входящие". Представьте, что вы владеете компанией, которая продает дизайнерские футболки. Как владелец компании вы получаете много сообщений с рисунками от художников. Сейчас вы открываете сообщения и вложения с помощью почтового клиента. Вместо того вы можете воспользоваться приложением Art Curator, чтобы в первую очередь просматривать вложения из папки "Входящие" и выбирать рисунки, которые вам нравятся. 

В этом примере демонстрируются следующие операции из API Почты Outlook Services: 
* [Извлечение папок](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#GetFolders)
* [Извлечение сообщений](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#Getmessages) (в том числе фильтрация и использование выборки) 
* [Извлечение вложений](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#GetAttachments)
* [Обновление сообщений](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#Updatemessages)
* [Создание и отправка сообщений](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#Sendmessages) (с вложением или без него) 

[![Office 365 Android Art Curator](../readme-images/artcurator_android.png)](https://youtu.be/4LOvkweDfhY "Щелкните, чтобы просмотреть пример в действии")

Необходимые компоненты
==
* [Android Studio](https://developer.android.com/sdk/index.html) 1.0 или более поздней версии
* Учетная запись Office 365. Вы можете подписаться на [план Office 365 для разработчиков](https://msdn.microsoft.com/en-us/library/office/fp179924.aspx), который включает ресурсы, необходимые для создания приложений Office 365.

**Примечание**<br/>
Убедитесь, что ваша подписка на Azure привязана к клиенту Office 365. Инструкции см. в записи блога команды Active Directory о [создании нескольких каталогов Windows Azure Active Directory и управлении ими](http://blogs.technet.com/b/ad/archive/2013/11/08/creating-and-managing-multiple-windows-azure-active-directories.aspx) в разделе, посвященном добавлению нового каталога. Дополнительные сведения см. в разделе о [настройке доступа к Azure Active Directory для Сайта разработчика](https://msdn.microsoft.com/office/office365/howto/setup-development-environment#bk_CreateAzureSubscription).

Импорт в Android Studio
==
* Клонируйте этот репозиторий.
* Откройте Android Studio.
  * Импортируйте проект (Eclipse ADT, Gradle и т. д.) и выберите ```settings.gradle``` целевого проекта.

Первый запуск
==
Это приложение содержит сведения о предварительной регистрации в Azure с разрешениями на **отправку почты от имени пользователя** и **чтение и создание писем от имени пользователя**.

Сведения о приложении находятся в файле ```com.microsoft.artcurator.conf.ServiceConstants```.
    
    public static final String CLIENT_ID = "1feaa784-0130-48d9-adeb-776fc65888c5";
    public static final String REDIRECT_URI = "https://useonlytoruntheartcuratorsample/";
        
Сведения о регистрации собственного клиентского приложения в Azure см. [здесь](https://msdn.microsoft.com/library/azure/dn132599.aspx#BKMK_Adding). 

При регистрации приложения укажите URI перенаправления. После этого получите идентификатор клиента на странице **НАСТРОЙКА** 
Приложение *должно* иметь разрешения на **отправку почты от имени пользователя** и **чтение и создание писем от имени пользователя**.

Дополнительные сведения см. в [примере O365-Android-Connect](https://github.com/OfficeDev/O365-Android-Connect)
Ограничения
==
* Поддержка типов файлов, отличных от ```PNG``` и ```JPG```
* Обработка одного сообщения с несколькими вложениями
* Подкачка (извлечение более 50 сообщений)
* Обработка уникальности имен папок
* Папкой отправки должна быть папка верхнего уровня

Вопросы и комментарии
==
* Если у вас не получается запустить этот пример, [сообщите о проблеме](https://github.com/OfficeDev/O365-Android-ArtCurator/issues)
* Общие вопросы об API Office 365 задавайте на сайте [Stack Overflow](http://stackoverflow.com/). Обязательно помечайте свои вопросы и комментарии тегами [Office365] и [outlook-restapi]

Дополнительные ресурсы
==
* [Начало работы с API Office 365 в приложениях](https://msdn.microsoft.com/en-us/office/office365/howto/getting-started-Office-365-APIs)
* [Центр разработки для Office (Android](http://dev.office.com/Android)
* [Обзор платформы API Office 365](http://stackoverflow.com/)
* [Art Curator для iOS](https://github.com/OfficeDev/O365-iOS-ArtCurator)
* [Art Curator для Windows Phone](https://github.com/OfficeDev/O365-WinPhone-ArtCurator)
* [Art Curator для веб-браузеров (веб-приложение на платформе Angular](https://github.com/OfficeDev/O365-Angular-ArtCurator)

Авторские права
==
(c) Корпорация Майкрософт (Microsoft Corporation), 2015. Все права защищены.

