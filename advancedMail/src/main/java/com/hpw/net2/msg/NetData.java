// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: NetData.proto

package com.hpw.net2.msg;

public final class NetData {
  private NetData() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  static com.google.protobuf.Descriptors.Descriptor
    internal_static_com_hpw_net_msg_NetMail_descriptor;
  static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_com_hpw_net_msg_NetMail_fieldAccessorTable;
  static com.google.protobuf.Descriptors.Descriptor
    internal_static_com_hpw_net_msg_NetMailInit_descriptor;
  static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_com_hpw_net_msg_NetMailInit_fieldAccessorTable;
  
  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\rNetData.proto\022\017com.hpw.net.msg\"\344\001\n\007Net" +
      "Mail\022\n\n\002id\030\001 \002(\003\022\022\n\nreceiverId\030\002 \002(\003\022\020\n\010" +
      "senderId\030\003 \002(\003\022\022\n\nsenderName\030\004 \002(\t\022\027\n\017at" +
      "tachmentState\030\005 \002(\005\022\014\n\004read\030\006 \002(\010\022\017\n\007sub" +
      "ject\030\007 \002(\t\022\020\n\010mailType\030\010 \002(\005\022\037\n\027attachme" +
      "ntContentFormat\030\n \001(\t\022\023\n\013sendingTime\030\013 \002" +
      "(\003\022\023\n\013senderVipLv\030\014 \001(\005\"\264\001\n\013NetMailInit\022" +
      "\027\n\017totoalItemCount\030\001 \002(\005\022\025\n\rtotalPageSiz" +
      "e\030\002 \002(\005\022\026\n\016currentPageNum\030\003 \002(\005\022\034\n\024curre" +
      "ntPageItemCount\030\004 \002(\005\022*\n\010mailList\030\005 \003(\0132",
      "\030.com.hpw.net.msg.NetMail\022\023\n\013senderVipLv" +
      "\030\006 \001(\005B\024\n\020com.hpw.net2.msgP\001"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_com_hpw_net_msg_NetMail_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_com_hpw_net_msg_NetMail_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_com_hpw_net_msg_NetMail_descriptor,
              new java.lang.String[] { "Id", "ReceiverId", "SenderId", "SenderName", "AttachmentState", "Read", "Subject", "MailType", "AttachmentContentFormat", "SendingTime", "SenderVipLv", },
              com.hpw.net2.msg.NetMail.class,
              com.hpw.net2.msg.NetMail.Builder.class);
          internal_static_com_hpw_net_msg_NetMailInit_descriptor =
            getDescriptor().getMessageTypes().get(1);
          internal_static_com_hpw_net_msg_NetMailInit_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_com_hpw_net_msg_NetMailInit_descriptor,
              new java.lang.String[] { "TotoalItemCount", "TotalPageSize", "CurrentPageNum", "CurrentPageItemCount", "MailList", "SenderVipLv", },
              com.hpw.net2.msg.NetMailInit.class,
              com.hpw.net2.msg.NetMailInit.Builder.class);
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }
  
  // @@protoc_insertion_point(outer_class_scope)
}
