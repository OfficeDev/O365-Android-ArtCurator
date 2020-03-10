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
[![Состояние сборки](https://travis-ci.org/OfficeDev/O365-Android-ArtCurator.svg?branch=master)](https://travis-ci.org/OfficeDev/O365-Android-ArtCurator)


В этом примере показано, как извлекать сообщения и вложения из Office 365 с помощью API Почты Outlook. Он построен для [iOS](https://github.com/OfficeDev/O365-iOS-ArtCurator), Android, [веб-браузеров (веб-приложение Angular)](https://github.com/OfficeDev/O365-Angular-ArtCurator) и [Windows Phone](https://github.com/OfficeDev/O365-WinPhone-ArtCurator). Ознакомьтесь с нашей [статьей на портале Medium](https://medium.com/@iambmelt/14296d0a25be).
<br />
<br />
Art Curator воплощает новый подход к просмотру папки "Входящие". Представьте, что вы владеете компанией, которая продает дизайнерские футболки. Как владелец компании вы получаете много сообщений с рисунками от художников. Сейчас вы открываете сообщения и вложения с помощью почтового клиента. Вместо этого с помощью приложения Art Curator вы можете сразу просматривать вложения из папки "Входящие", выбирая понравившиеся рисунки. 

Этот пример демонстрирует следующие операции из API Почты Outlook:
* [получение папок](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#GetFolders)
* [получение сообщений](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#Getmessages) (в том числе фильтрация и использование выборки)
* [получение вложений](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#GetAttachments)
* [обновление сообщений](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#Updatemessages)
* [создание и отправка сообщений](https://msdn.microsoft.com/office/office365/APi/mail-rest-operations#Sendmessages) (с вложениями и без них). 

[![Art Curator для Office 365 на платформе Android](/readme-images/artcurator_android.png)![Щелкните, чтобы увидеть работу примера](/readme-images/artcurator_android.png)

Необходимые компоненты
==
* [Android Studio](https://developer.android.com/sdk/index.html) версии 1.0+
* Учетная запись Office 365. Вы можете подписаться на [план Office 365 для разработчиков](https://msdn.microsoft.com/en-us/library/office/fp179924.aspx), который включает ресурсы, необходимые для создания приложений Office 365. 

**Примечание**<br/>
Убедитесь, что ваша подписка на Azure привязана к клиенту Office 365. Соответствующие инструкции содержатся в блоге команды Active Directory [Создание нескольких каталогов Windows Azure Active Directory и управление ими](http://blogs.technet.com/b/ad/archive/2013/11/08/creating-and-managing-multiple-windows-azure-active-directories.aspx), а именно в разделе "Добавление нового каталога". Для получения дополнительных сведений можно ознакомиться со статьей [Настройка доступа к сайту разработчика с помощью Azure Active Directory](https://msdn.microsoft.com/office/office365/howto/setup-development-environment#bk_CreateAzureSubscription).

Импорт в Android Studio
==
* Клонируйте этот репозиторий
* Откройте Android Studio
  * Импортируйте проект (Eclipse ADT, Gradle и т.д.) и выберите раздел ```settings.gradle``` целевого проекта.

Первый запуск
==
Это приложение содержит сведения о предварительной регистрации в Azure с разрешениями на **отправку почты от имени пользователя** и **чтение и создание писем от имени пользователя**.

Сведения о приложении находятся в файле ```com.microsoft.artcurator.conf.ServiceConstants```.
    
    public static final String CLIENT_ID = "1feaa784-0130-48d9-adeb-776fc65888c5";
    public static final String REDIRECT_URI = "https://useonlytoruntheartcuratorsample/";
        
Что касается вашего приложения, [зарегистрируйте собственное клиентское приложение на Azure](https://msdn.microsoft.com/library/azure/dn132599.aspx#BKMK_Adding). 

При регистрации приложения укажите URI перенаправления. После этого получите идентификатор клиента на странице **НАСТРОЙКА**.
Приложение *должно* иметь разрешения на **отправку почты от имени пользователя** и **чтение и создание писем от имени пользователя**.

Дополнительные сведения см. в статье [Пример приложения O365-Android-Connect](https://github.com/OfficeDev/O365-Android-Connect) Ограничения
==
* Поддержка файлов помимо ```.png``` и ```.jpg```
* Обработка одного сообщения электронной почты с несколькими вложениями
* Разбиение на страницы (получение более чем 50 сообщений)
* Обработка уникальности имени папки
* Папка для отправки должна быть папкой верхнего уровня

Вопросы и комментарии
==
* Если у вас возникли проблемы с запуском этого примера, [сообщите о неполадке](https://github.com/OfficeDev/O365-Android-ArtCurator/issues)
* Общие вопросы про API Office 365 публикуйте в[Stack Overflow](http://stackoverflow.com/). Обязательно помечайте свои вопросы и комментарии тегами \[Office365] и \[outlook-restapi].

Дополнительные ресурсы
==
* [Начало работы с API Office 365 в приложениях](https://msdn.microsoft.com/en-us/office/office365/howto/getting-started-Office-365-APIs)
* [Центр разработки для Office (Android)](http://dev.office.com/Android)
* [Общие сведения о платформе API Office 365](http://stackoverflow.com/)
* [Art Curator для iOS](https://github.com/OfficeDev/O365-iOS-ArtCurator)
* [Art Curator для Windows Phone](https://github.com/OfficeDev/O365-WinPhone-ArtCurator)
* [Art Curator для веб-браузеров (веб-приложение Angular)](https://github.com/OfficeDev/O365-Angular-ArtCurator)

Авторские права
==
(c) Корпорация Майкрософт (Microsoft Corporation), 2015. Все права защищены.


Этот проект соответствует [Правилам поведения разработчиков открытого кода Майкрософт](https://opensource.microsoft.com/codeofconduct/). Дополнительные сведения см. в разделе [часто задаваемых вопросов о правилах поведения](https://opensource.microsoft.com/codeofconduct/faq/). Если у вас возникли вопросы или замечания, напишите нам по адресу [opencode@microsoft.com](mailto:opencode@microsoft.com).
