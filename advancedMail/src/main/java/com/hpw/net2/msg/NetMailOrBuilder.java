// Generated by the protocol buffer compiler.  DO NOT EDIT!

package com.hpw.net2.msg;

public interface NetMailOrBuilder
    extends com.google.protobuf.MessageOrBuilder {
  
  // required int64 id = 1;
  boolean hasId();
  long getId();
  
  // required int64 receiverId = 2;
  boolean hasReceiverId();
  long getReceiverId();
  
  // required int64 senderId = 3;
  boolean hasSenderId();
  long getSenderId();
  
  // required string senderName = 4;
  boolean hasSenderName();
  String getSenderName();
  
  // required int32 attachmentState = 5;
  boolean hasAttachmentState();
  int getAttachmentState();
  
  // required bool read = 6;
  boolean hasRead();
  boolean getRead();
  
  // required string subject = 7;
  boolean hasSubject();
  String getSubject();
  
  // required int32 mailType = 8;
  boolean hasMailType();
  int getMailType();
  
  // optional string attachmentContentFormat = 10;
  boolean hasAttachmentContentFormat();
  String getAttachmentContentFormat();
  
  // required int64 sendingTime = 11;
  boolean hasSendingTime();
  long getSendingTime();
  
  // optional int32 senderVipLv = 12;
  boolean hasSenderVipLv();
  int getSenderVipLv();
}
