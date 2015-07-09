/*
 *  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 *  See full license at the bottom of this file.
 */
package com.microsoft.artcurator.net;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.microsoft.outlookservices.Attachment;
import com.microsoft.outlookservices.BodyType;
import com.microsoft.outlookservices.EmailAddress;
import com.microsoft.outlookservices.FileAttachment;
import com.microsoft.outlookservices.Folder;
import com.microsoft.outlookservices.ItemBody;
import com.microsoft.outlookservices.Message;
import com.microsoft.outlookservices.Recipient;
import com.microsoft.outlookservices.odata.OutlookClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines the {@link OutlookClient} actions necessary for Art Curator to filter and
 * fetch the targeted {@link Message}s from Outlook
 */
public class MessagesLoader {

    /**
     * Replies to a Message
     *
     * @param client   the client to make the request
     * @param msgId    the Message to which the response should be sent
     * @param msgText  the body text of the outgoing Message
     * @param callback callback to notify on success/failure
     */
    public static void replyAsync(
            OutlookClient client,
            String msgId,
            String msgText,
            FutureCallback<Integer> callback) {
        ListenableFuture<Integer> future =
                client.getMe()
                        .getFolder("Draft")
                        .getMessages()
                        .getById(msgId)
                        .getOperations()
                        .reply(msgText);
        Futures.addCallback(future, callback);
    }


    /**
     * Marks a Message as read
     *
     * @param client   the client to make the request
     * @param msgId    the Message to mark read
     * @param callback callback to notify on success/failure
     */
    public static void markReadAsync(
            OutlookClient client,
            String msgId,
            FutureCallback<String> callback) {
        ListenableFuture<String> future =
                client.getMe()
                        .getMessage(msgId)
                        .updateRaw("{\"IsRead\" : true}");
        Futures.addCallback(future, callback);
    }

    /**
     * Fetches the Outlook Folders visible to this user
     *
     * @param client   the client to make the request
     * @param callback callback to notify on success/failure
     */
    public static void getFoldersAsync(
            OutlookClient client,
            FutureCallback<List<Folder>> callback) {
        ListenableFuture<List<Folder>> future =
                client.getMe()
                        .getFolders()
                        .select("Id,DisplayName")
                        .read();
        Futures.addCallback(future, callback);
    }

    /**
     * Requests Message with Attachments based on the provided folder id
     *
     * @param client   the client to make the request
     * @param folderId the folder to inspect
     * @param callback callback to notify on success/failure
     */
    public static void getMessagesWithAttachmentsFromFolderAsync(
            OutlookClient client,
            String folderId,
            FutureCallback<List<Message>> callback) {
        ListenableFuture<List<Message>> future = client
                .getMe()
                .getFolder(folderId)
                .getMessages()
                .filter("HasAttachments eq true and IsRead eq false")
                .top(50)
                .read();
        Futures.addCallback(future, callback);
    }

    /**
     * Requests Attachment information about a given Message
     *
     * @param client   the client to make the request
     * @param msgId    the Message for which Attachment data should be loaded
     * @param callback callback to notify on success/failure
     */
    public static void getAttachmentContentTypeAsync(
            OutlookClient client,
            String msgId,
            FutureCallback<List<Attachment>> callback) {
        ListenableFuture<List<Attachment>> future = client
                .getMe()
                .getMessage(msgId)
                .getAttachments()
                .select("ContentType")
                .read();
        Futures.addCallback(future, callback);
    }

    /**
     * Loads an Attachment
     *
     * @param client       the client to make the request
     * @param msgId        the Message hosting the Attachment
     * @param attachmentId the Attachment to load
     * @param callback     callback to notify on success/failure
     */
    public static void getAttachmentAsync(
            OutlookClient client,
            String msgId,
            String attachmentId,
            FutureCallback<Attachment> callback) {
        ListenableFuture<Attachment> attachmentListenableFuture =
                client.getMe()
                        .getMessage(msgId)
                        .getAttachment(attachmentId).read();
        Futures.addCallback(attachmentListenableFuture, callback);
    }

    /**
     * Sends an email with an Attachment
     *
     * @param client       the client to make the request
     * @param subject      the subject message to use
     * @param body         the body (plain-text) of the outbound message
     * @param email        the destination (recipient)
     * @param contentType  the content type of the attachment
     * @param contentBytes the length of the attachment (in bytes)
     * @param fileName     the filename of the attachment
     * @param callback     callback to notify on success/failure
     */
    public static void sendMailWithAttachmentAsync(
            OutlookClient client,
            String subject,
            String body,
            final String email,
            String contentType,
            byte[] contentBytes,
            String fileName,
            FutureCallback<Integer> callback) {
        ItemBody itemBody = new ItemBody();
        itemBody.setContent(body);
        itemBody.setContentType(BodyType.Text);

        Message message = new Message();
        message.setSubject(subject);
        message.setBody(itemBody);
        message.setToRecipients(new ArrayList<Recipient>() {{
            Recipient recipient = new Recipient();
            EmailAddress address = new EmailAddress();
            address.setAddress(email);
            recipient.setEmailAddress(address);
            add(recipient);
        }});

        final FileAttachment imageFileAttachment = new FileAttachment();
        imageFileAttachment.setContentBytes(contentBytes);
        imageFileAttachment.setContentType(contentType);
        imageFileAttachment.setSize(contentBytes.length);
        imageFileAttachment.setName(fileName);

        message.setAttachments(new ArrayList<Attachment>() {{
            add(imageFileAttachment);
        }});

        ListenableFuture<Integer> future =
                client.getMe().getOperations().sendMail(message, true);
        Futures.addCallback(future, callback);
    }

}
// *********************************************************
//
// O365-Android-Art Curator
//
// Copyright (c) Microsoft Corporation
// All rights reserved.
//
// MIT License:
// Permission is hereby granted, free of charge, to any person obtaining
// a copy of this software and associated documentation files (the
// "Software"), to deal in the Software without restriction, including
// without limitation the rights to use, copy, modify, merge, publish,
// distribute, sublicense, and/or sell copies of the Software, and to
// permit persons to whom the Software is furnished to do so, subject to
// the following conditions:
//
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
// EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
// NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
// LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
// OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
// WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
//
// *********************************************************