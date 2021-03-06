// Generated by the protocol buffer compiler.  DO NOT EDIT!

package com.hpw.net2.msg;

public  final class NetMail extends
    com.google.protobuf.GeneratedMessage
    implements NetMailOrBuilder {
  // Use NetMail.newBuilder() to construct.
  private NetMail(Builder builder) {
    super(builder);
  }
  private NetMail(boolean noInit) {}
  
  private static final NetMail defaultInstance;
  public static NetMail getDefaultInstance() {
    return defaultInstance;
  }
  
  public NetMail getDefaultInstanceForType() {
    return defaultInstance;
  }
  
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.hpw.net2.msg.NetData.internal_static_com_hpw_net_msg_NetMail_descriptor;
  }
  
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.hpw.net2.msg.NetData.internal_static_com_hpw_net_msg_NetMail_fieldAccessorTable;
  }
  
  private int bitField0_;
  // required int64 id = 1;
  public static final int ID_FIELD_NUMBER = 1;
  private long id_;
  public boolean hasId() {
    return ((bitField0_ & 0x00000001) == 0x00000001);
  }
  public long getId() {
    return id_;
  }
  
  // required int64 receiverId = 2;
  public static final int RECEIVERID_FIELD_NUMBER = 2;
  private long receiverId_;
  public boolean hasReceiverId() {
    return ((bitField0_ & 0x00000002) == 0x00000002);
  }
  public long getReceiverId() {
    return receiverId_;
  }
  
  // required int64 senderId = 3;
  public static final int SENDERID_FIELD_NUMBER = 3;
  private long senderId_;
  public boolean hasSenderId() {
    return ((bitField0_ & 0x00000004) == 0x00000004);
  }
  public long getSenderId() {
    return senderId_;
  }
  
  // required string senderName = 4;
  public static final int SENDERNAME_FIELD_NUMBER = 4;
  private java.lang.Object senderName_;
  public boolean hasSenderName() {
    return ((bitField0_ & 0x00000008) == 0x00000008);
  }
  public String getSenderName() {
    java.lang.Object ref = senderName_;
    if (ref instanceof String) {
      return (String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      String s = bs.toStringUtf8();
      if (com.google.protobuf.Internal.isValidUtf8(bs)) {
        senderName_ = s;
      }
      return s;
    }
  }
  private com.google.protobuf.ByteString getSenderNameBytes() {
    java.lang.Object ref = senderName_;
    if (ref instanceof String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8((String) ref);
      senderName_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }
  
  // required int32 attachmentState = 5;
  public static final int ATTACHMENTSTATE_FIELD_NUMBER = 5;
  private int attachmentState_;
  public boolean hasAttachmentState() {
    return ((bitField0_ & 0x00000010) == 0x00000010);
  }
  public int getAttachmentState() {
    return attachmentState_;
  }
  
  // required bool read = 6;
  public static final int READ_FIELD_NUMBER = 6;
  private boolean read_;
  public boolean hasRead() {
    return ((bitField0_ & 0x00000020) == 0x00000020);
  }
  public boolean getRead() {
    return read_;
  }
  
  // required string subject = 7;
  public static final int SUBJECT_FIELD_NUMBER = 7;
  private java.lang.Object subject_;
  public boolean hasSubject() {
    return ((bitField0_ & 0x00000040) == 0x00000040);
  }
  public String getSubject() {
    java.lang.Object ref = subject_;
    if (ref instanceof String) {
      return (String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      String s = bs.toStringUtf8();
      if (com.google.protobuf.Internal.isValidUtf8(bs)) {
        subject_ = s;
      }
      return s;
    }
  }
  private com.google.protobuf.ByteString getSubjectBytes() {
    java.lang.Object ref = subject_;
    if (ref instanceof String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8((String) ref);
      subject_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }
  
  // required int32 mailType = 8;
  public static final int MAILTYPE_FIELD_NUMBER = 8;
  private int mailType_;
  public boolean hasMailType() {
    return ((bitField0_ & 0x00000080) == 0x00000080);
  }
  public int getMailType() {
    return mailType_;
  }
  
  // optional string attachmentContentFormat = 10;
  public static final int ATTACHMENTCONTENTFORMAT_FIELD_NUMBER = 10;
  private java.lang.Object attachmentContentFormat_;
  public boolean hasAttachmentContentFormat() {
    return ((bitField0_ & 0x00000100) == 0x00000100);
  }
  public String getAttachmentContentFormat() {
    java.lang.Object ref = attachmentContentFormat_;
    if (ref instanceof String) {
      return (String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      String s = bs.toStringUtf8();
      if (com.google.protobuf.Internal.isValidUtf8(bs)) {
        attachmentContentFormat_ = s;
      }
      return s;
    }
  }
  private com.google.protobuf.ByteString getAttachmentContentFormatBytes() {
    java.lang.Object ref = attachmentContentFormat_;
    if (ref instanceof String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8((String) ref);
      attachmentContentFormat_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }
  
  // required int64 sendingTime = 11;
  public static final int SENDINGTIME_FIELD_NUMBER = 11;
  private long sendingTime_;
  public boolean hasSendingTime() {
    return ((bitField0_ & 0x00000200) == 0x00000200);
  }
  public long getSendingTime() {
    return sendingTime_;
  }
  
  // optional int32 senderVipLv = 12;
  public static final int SENDERVIPLV_FIELD_NUMBER = 12;
  private int senderVipLv_;
  public boolean hasSenderVipLv() {
    return ((bitField0_ & 0x00000400) == 0x00000400);
  }
  public int getSenderVipLv() {
    return senderVipLv_;
  }
  
  private void initFields() {
    id_ = 0L;
    receiverId_ = 0L;
    senderId_ = 0L;
    senderName_ = "";
    attachmentState_ = 0;
    read_ = false;
    subject_ = "";
    mailType_ = 0;
    attachmentContentFormat_ = "";
    sendingTime_ = 0L;
    senderVipLv_ = 0;
  }
  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized != -1) return isInitialized == 1;
    
    if (!hasId()) {
      memoizedIsInitialized = 0;
      return false;
    }
    if (!hasReceiverId()) {
      memoizedIsInitialized = 0;
      return false;
    }
    if (!hasSenderId()) {
      memoizedIsInitialized = 0;
      return false;
    }
    if (!hasSenderName()) {
      memoizedIsInitialized = 0;
      return false;
    }
    if (!hasAttachmentState()) {
      memoizedIsInitialized = 0;
      return false;
    }
    if (!hasRead()) {
      memoizedIsInitialized = 0;
      return false;
    }
    if (!hasSubject()) {
      memoizedIsInitialized = 0;
      return false;
    }
    if (!hasMailType()) {
      memoizedIsInitialized = 0;
      return false;
    }
    if (!hasSendingTime()) {
      memoizedIsInitialized = 0;
      return false;
    }
    memoizedIsInitialized = 1;
    return true;
  }
  
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    getSerializedSize();
    if (((bitField0_ & 0x00000001) == 0x00000001)) {
      output.writeInt64(1, id_);
    }
    if (((bitField0_ & 0x00000002) == 0x00000002)) {
      output.writeInt64(2, receiverId_);
    }
    if (((bitField0_ & 0x00000004) == 0x00000004)) {
      output.writeInt64(3, senderId_);
    }
    if (((bitField0_ & 0x00000008) == 0x00000008)) {
      output.writeBytes(4, getSenderNameBytes());
    }
    if (((bitField0_ & 0x00000010) == 0x00000010)) {
      output.writeInt32(5, attachmentState_);
    }
    if (((bitField0_ & 0x00000020) == 0x00000020)) {
      output.writeBool(6, read_);
    }
    if (((bitField0_ & 0x00000040) == 0x00000040)) {
      output.writeBytes(7, getSubjectBytes());
    }
    if (((bitField0_ & 0x00000080) == 0x00000080)) {
      output.writeInt32(8, mailType_);
    }
    if (((bitField0_ & 0x00000100) == 0x00000100)) {
      output.writeBytes(10, getAttachmentContentFormatBytes());
    }
    if (((bitField0_ & 0x00000200) == 0x00000200)) {
      output.writeInt64(11, sendingTime_);
    }
    if (((bitField0_ & 0x00000400) == 0x00000400)) {
      output.writeInt32(12, senderVipLv_);
    }
    getUnknownFields().writeTo(output);
  }
  
  private int memoizedSerializedSize = -1;
  public int getSerializedSize() {
    int size = memoizedSerializedSize;
    if (size != -1) return size;
  
    size = 0;
    if (((bitField0_ & 0x00000001) == 0x00000001)) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(1, id_);
    }
    if (((bitField0_ & 0x00000002) == 0x00000002)) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(2, receiverId_);
    }
    if (((bitField0_ & 0x00000004) == 0x00000004)) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(3, senderId_);
    }
    if (((bitField0_ & 0x00000008) == 0x00000008)) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(4, getSenderNameBytes());
    }
    if (((bitField0_ & 0x00000010) == 0x00000010)) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(5, attachmentState_);
    }
    if (((bitField0_ & 0x00000020) == 0x00000020)) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(6, read_);
    }
    if (((bitField0_ & 0x00000040) == 0x00000040)) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(7, getSubjectBytes());
    }
    if (((bitField0_ & 0x00000080) == 0x00000080)) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(8, mailType_);
    }
    if (((bitField0_ & 0x00000100) == 0x00000100)) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(10, getAttachmentContentFormatBytes());
    }
    if (((bitField0_ & 0x00000200) == 0x00000200)) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(11, sendingTime_);
    }
    if (((bitField0_ & 0x00000400) == 0x00000400)) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(12, senderVipLv_);
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSerializedSize = size;
    return size;
  }
  
  private static final long serialVersionUID = 0L;
  @java.lang.Override
  protected java.lang.Object writeReplace()
      throws java.io.ObjectStreamException {
    return super.writeReplace();
  }
  
  public static com.hpw.net2.msg.NetMail parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return newBuilder().mergeFrom(data).buildParsed();
  }
  public static com.hpw.net2.msg.NetMail parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return newBuilder().mergeFrom(data, extensionRegistry)
             .buildParsed();
  }
  public static com.hpw.net2.msg.NetMail parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return newBuilder().mergeFrom(data).buildParsed();
  }
  public static com.hpw.net2.msg.NetMail parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return newBuilder().mergeFrom(data, extensionRegistry)
             .buildParsed();
  }
  public static com.hpw.net2.msg.NetMail parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return newBuilder().mergeFrom(input).buildParsed();
  }
  public static com.hpw.net2.msg.NetMail parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return newBuilder().mergeFrom(input, extensionRegistry)
             .buildParsed();
  }
  public static com.hpw.net2.msg.NetMail parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    Builder builder = newBuilder();
    if (builder.mergeDelimitedFrom(input)) {
      return builder.buildParsed();
    } else {
      return null;
    }
  }
  public static com.hpw.net2.msg.NetMail parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    Builder builder = newBuilder();
    if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
      return builder.buildParsed();
    } else {
      return null;
    }
  }
  public static com.hpw.net2.msg.NetMail parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return newBuilder().mergeFrom(input).buildParsed();
  }
  public static com.hpw.net2.msg.NetMail parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return newBuilder().mergeFrom(input, extensionRegistry)
             .buildParsed();
  }
  
  public static Builder newBuilder() { return Builder.create(); }
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder(com.hpw.net2.msg.NetMail prototype) {
    return newBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() { return newBuilder(this); }
  
  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessage.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder>
     implements com.hpw.net2.msg.NetMailOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.hpw.net2.msg.NetData.internal_static_com_hpw_net_msg_NetMail_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.hpw.net2.msg.NetData.internal_static_com_hpw_net_msg_NetMail_fieldAccessorTable;
    }
    
    // Construct using com.hpw.net2.msg.NetMail.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }
    
    private Builder(BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
      }
    }
    private static Builder create() {
      return new Builder();
    }
    
    public Builder clear() {
      super.clear();
      id_ = 0L;
      bitField0_ = (bitField0_ & ~0x00000001);
      receiverId_ = 0L;
      bitField0_ = (bitField0_ & ~0x00000002);
      senderId_ = 0L;
      bitField0_ = (bitField0_ & ~0x00000004);
      senderName_ = "";
      bitField0_ = (bitField0_ & ~0x00000008);
      attachmentState_ = 0;
      bitField0_ = (bitField0_ & ~0x00000010);
      read_ = false;
      bitField0_ = (bitField0_ & ~0x00000020);
      subject_ = "";
      bitField0_ = (bitField0_ & ~0x00000040);
      mailType_ = 0;
      bitField0_ = (bitField0_ & ~0x00000080);
      attachmentContentFormat_ = "";
      bitField0_ = (bitField0_ & ~0x00000100);
      sendingTime_ = 0L;
      bitField0_ = (bitField0_ & ~0x00000200);
      senderVipLv_ = 0;
      bitField0_ = (bitField0_ & ~0x00000400);
      return this;
    }
    
    public Builder clone() {
      return create().mergeFrom(buildPartial());
    }
    
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.hpw.net2.msg.NetMail.getDescriptor();
    }
    
    public com.hpw.net2.msg.NetMail getDefaultInstanceForType() {
      return com.hpw.net2.msg.NetMail.getDefaultInstance();
    }
    
    public com.hpw.net2.msg.NetMail build() {
      com.hpw.net2.msg.NetMail result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }
    
    private com.hpw.net2.msg.NetMail buildParsed()
        throws com.google.protobuf.InvalidProtocolBufferException {
      com.hpw.net2.msg.NetMail result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(
          result).asInvalidProtocolBufferException();
      }
      return result;
    }
    
    public com.hpw.net2.msg.NetMail buildPartial() {
      com.hpw.net2.msg.NetMail result = new com.hpw.net2.msg.NetMail(this);
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
        to_bitField0_ |= 0x00000001;
      }
      result.id_ = id_;
      if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
        to_bitField0_ |= 0x00000002;
      }
      result.receiverId_ = receiverId_;
      if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
        to_bitField0_ |= 0x00000004;
      }
      result.senderId_ = senderId_;
      if (((from_bitField0_ & 0x00000008) == 0x00000008)) {
        to_bitField0_ |= 0x00000008;
      }
      result.senderName_ = senderName_;
      if (((from_bitField0_ & 0x00000010) == 0x00000010)) {
        to_bitField0_ |= 0x00000010;
      }
      result.attachmentState_ = attachmentState_;
      if (((from_bitField0_ & 0x00000020) == 0x00000020)) {
        to_bitField0_ |= 0x00000020;
      }
      result.read_ = read_;
      if (((from_bitField0_ & 0x00000040) == 0x00000040)) {
        to_bitField0_ |= 0x00000040;
      }
      result.subject_ = subject_;
      if (((from_bitField0_ & 0x00000080) == 0x00000080)) {
        to_bitField0_ |= 0x00000080;
      }
      result.mailType_ = mailType_;
      if (((from_bitField0_ & 0x00000100) == 0x00000100)) {
        to_bitField0_ |= 0x00000100;
      }
      result.attachmentContentFormat_ = attachmentContentFormat_;
      if (((from_bitField0_ & 0x00000200) == 0x00000200)) {
        to_bitField0_ |= 0x00000200;
      }
      result.sendingTime_ = sendingTime_;
      if (((from_bitField0_ & 0x00000400) == 0x00000400)) {
        to_bitField0_ |= 0x00000400;
      }
      result.senderVipLv_ = senderVipLv_;
      result.bitField0_ = to_bitField0_;
      onBuilt();
      return result;
    }
    
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.hpw.net2.msg.NetMail) {
        return mergeFrom((com.hpw.net2.msg.NetMail)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }
    
    public Builder mergeFrom(com.hpw.net2.msg.NetMail other) {
      if (other == com.hpw.net2.msg.NetMail.getDefaultInstance()) return this;
      if (other.hasId()) {
        setId(other.getId());
      }
      if (other.hasReceiverId()) {
        setReceiverId(other.getReceiverId());
      }
      if (other.hasSenderId()) {
        setSenderId(other.getSenderId());
      }
      if (other.hasSenderName()) {
        setSenderName(other.getSenderName());
      }
      if (other.hasAttachmentState()) {
        setAttachmentState(other.getAttachmentState());
      }
      if (other.hasRead()) {
        setRead(other.getRead());
      }
      if (other.hasSubject()) {
        setSubject(other.getSubject());
      }
      if (other.hasMailType()) {
        setMailType(other.getMailType());
      }
      if (other.hasAttachmentContentFormat()) {
        setAttachmentContentFormat(other.getAttachmentContentFormat());
      }
      if (other.hasSendingTime()) {
        setSendingTime(other.getSendingTime());
      }
      if (other.hasSenderVipLv()) {
        setSenderVipLv(other.getSenderVipLv());
      }
      this.mergeUnknownFields(other.getUnknownFields());
      return this;
    }
    
    public final boolean isInitialized() {
      if (!hasId()) {
        
        return false;
      }
      if (!hasReceiverId()) {
        
        return false;
      }
      if (!hasSenderId()) {
        
        return false;
      }
      if (!hasSenderName()) {
        
        return false;
      }
      if (!hasAttachmentState()) {
        
        return false;
      }
      if (!hasRead()) {
        
        return false;
      }
      if (!hasSubject()) {
        
        return false;
      }
      if (!hasMailType()) {
        
        return false;
      }
      if (!hasSendingTime()) {
        
        return false;
      }
      return true;
    }
    
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder(
          this.getUnknownFields());
      while (true) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            this.setUnknownFields(unknownFields.build());
            onChanged();
            return this;
          default: {
            if (!parseUnknownField(input, unknownFields,
                                   extensionRegistry, tag)) {
              this.setUnknownFields(unknownFields.build());
              onChanged();
              return this;
            }
            break;
          }
          case 8: {
            bitField0_ |= 0x00000001;
            id_ = input.readInt64();
            break;
          }
          case 16: {
            bitField0_ |= 0x00000002;
            receiverId_ = input.readInt64();
            break;
          }
          case 24: {
            bitField0_ |= 0x00000004;
            senderId_ = input.readInt64();
            break;
          }
          case 34: {
            bitField0_ |= 0x00000008;
            senderName_ = input.readBytes();
            break;
          }
          case 40: {
            bitField0_ |= 0x00000010;
            attachmentState_ = input.readInt32();
            break;
          }
          case 48: {
            bitField0_ |= 0x00000020;
            read_ = input.readBool();
            break;
          }
          case 58: {
            bitField0_ |= 0x00000040;
            subject_ = input.readBytes();
            break;
          }
          case 64: {
            bitField0_ |= 0x00000080;
            mailType_ = input.readInt32();
            break;
          }
          case 82: {
            bitField0_ |= 0x00000100;
            attachmentContentFormat_ = input.readBytes();
            break;
          }
          case 88: {
            bitField0_ |= 0x00000200;
            sendingTime_ = input.readInt64();
            break;
          }
          case 96: {
            bitField0_ |= 0x00000400;
            senderVipLv_ = input.readInt32();
            break;
          }
        }
      }
    }
    
    private int bitField0_;
    
    // required int64 id = 1;
    private long id_ ;
    public boolean hasId() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    public long getId() {
      return id_;
    }
    public Builder setId(long value) {
      bitField0_ |= 0x00000001;
      id_ = value;
      onChanged();
      return this;
    }
    public Builder clearId() {
      bitField0_ = (bitField0_ & ~0x00000001);
      id_ = 0L;
      onChanged();
      return this;
    }
    
    // required int64 receiverId = 2;
    private long receiverId_ ;
    public boolean hasReceiverId() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    public long getReceiverId() {
      return receiverId_;
    }
    public Builder setReceiverId(long value) {
      bitField0_ |= 0x00000002;
      receiverId_ = value;
      onChanged();
      return this;
    }
    public Builder clearReceiverId() {
      bitField0_ = (bitField0_ & ~0x00000002);
      receiverId_ = 0L;
      onChanged();
      return this;
    }
    
    // required int64 senderId = 3;
    private long senderId_ ;
    public boolean hasSenderId() {
      return ((bitField0_ & 0x00000004) == 0x00000004);
    }
    public long getSenderId() {
      return senderId_;
    }
    public Builder setSenderId(long value) {
      bitField0_ |= 0x00000004;
      senderId_ = value;
      onChanged();
      return this;
    }
    public Builder clearSenderId() {
      bitField0_ = (bitField0_ & ~0x00000004);
      senderId_ = 0L;
      onChanged();
      return this;
    }
    
    // required string senderName = 4;
    private java.lang.Object senderName_ = "";
    public boolean hasSenderName() {
      return ((bitField0_ & 0x00000008) == 0x00000008);
    }
    public String getSenderName() {
      java.lang.Object ref = senderName_;
      if (!(ref instanceof String)) {
        String s = ((com.google.protobuf.ByteString) ref).toStringUtf8();
        senderName_ = s;
        return s;
      } else {
        return (String) ref;
      }
    }
    public Builder setSenderName(String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000008;
      senderName_ = value;
      onChanged();
      return this;
    }
    public Builder clearSenderName() {
      bitField0_ = (bitField0_ & ~0x00000008);
      senderName_ = getDefaultInstance().getSenderName();
      onChanged();
      return this;
    }
    void setSenderName(com.google.protobuf.ByteString value) {
      bitField0_ |= 0x00000008;
      senderName_ = value;
      onChanged();
    }
    
    // required int32 attachmentState = 5;
    private int attachmentState_ ;
    public boolean hasAttachmentState() {
      return ((bitField0_ & 0x00000010) == 0x00000010);
    }
    public int getAttachmentState() {
      return attachmentState_;
    }
    public Builder setAttachmentState(int value) {
      bitField0_ |= 0x00000010;
      attachmentState_ = value;
      onChanged();
      return this;
    }
    public Builder clearAttachmentState() {
      bitField0_ = (bitField0_ & ~0x00000010);
      attachmentState_ = 0;
      onChanged();
      return this;
    }
    
    // required bool read = 6;
    private boolean read_ ;
    public boolean hasRead() {
      return ((bitField0_ & 0x00000020) == 0x00000020);
    }
    public boolean getRead() {
      return read_;
    }
    public Builder setRead(boolean value) {
      bitField0_ |= 0x00000020;
      read_ = value;
      onChanged();
      return this;
    }
    public Builder clearRead() {
      bitField0_ = (bitField0_ & ~0x00000020);
      read_ = false;
      onChanged();
      return this;
    }
    
    // required string subject = 7;
    private java.lang.Object subject_ = "";
    public boolean hasSubject() {
      return ((bitField0_ & 0x00000040) == 0x00000040);
    }
    public String getSubject() {
      java.lang.Object ref = subject_;
      if (!(ref instanceof String)) {
        String s = ((com.google.protobuf.ByteString) ref).toStringUtf8();
        subject_ = s;
        return s;
      } else {
        return (String) ref;
      }
    }
    public Builder setSubject(String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000040;
      subject_ = value;
      onChanged();
      return this;
    }
    public Builder clearSubject() {
      bitField0_ = (bitField0_ & ~0x00000040);
      subject_ = getDefaultInstance().getSubject();
      onChanged();
      return this;
    }
    void setSubject(com.google.protobuf.ByteString value) {
      bitField0_ |= 0x00000040;
      subject_ = value;
      onChanged();
    }
    
    // required int32 mailType = 8;
    private int mailType_ ;
    public boolean hasMailType() {
      return ((bitField0_ & 0x00000080) == 0x00000080);
    }
    public int getMailType() {
      return mailType_;
    }
    public Builder setMailType(int value) {
      bitField0_ |= 0x00000080;
      mailType_ = value;
      onChanged();
      return this;
    }
    public Builder clearMailType() {
      bitField0_ = (bitField0_ & ~0x00000080);
      mailType_ = 0;
      onChanged();
      return this;
    }
    
    // optional string attachmentContentFormat = 10;
    private java.lang.Object attachmentContentFormat_ = "";
    public boolean hasAttachmentContentFormat() {
      return ((bitField0_ & 0x00000100) == 0x00000100);
    }
    public String getAttachmentContentFormat() {
      java.lang.Object ref = attachmentContentFormat_;
      if (!(ref instanceof String)) {
        String s = ((com.google.protobuf.ByteString) ref).toStringUtf8();
        attachmentContentFormat_ = s;
        return s;
      } else {
        return (String) ref;
      }
    }
    public Builder setAttachmentContentFormat(String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000100;
      attachmentContentFormat_ = value;
      onChanged();
      return this;
    }
    public Builder clearAttachmentContentFormat() {
      bitField0_ = (bitField0_ & ~0x00000100);
      attachmentContentFormat_ = getDefaultInstance().getAttachmentContentFormat();
      onChanged();
      return this;
    }
    void setAttachmentContentFormat(com.google.protobuf.ByteString value) {
      bitField0_ |= 0x00000100;
      attachmentContentFormat_ = value;
      onChanged();
    }
    
    // required int64 sendingTime = 11;
    private long sendingTime_ ;
    public boolean hasSendingTime() {
      return ((bitField0_ & 0x00000200) == 0x00000200);
    }
    public long getSendingTime() {
      return sendingTime_;
    }
    public Builder setSendingTime(long value) {
      bitField0_ |= 0x00000200;
      sendingTime_ = value;
      onChanged();
      return this;
    }
    public Builder clearSendingTime() {
      bitField0_ = (bitField0_ & ~0x00000200);
      sendingTime_ = 0L;
      onChanged();
      return this;
    }
    
    // optional int32 senderVipLv = 12;
    private int senderVipLv_ ;
    public boolean hasSenderVipLv() {
      return ((bitField0_ & 0x00000400) == 0x00000400);
    }
    public int getSenderVipLv() {
      return senderVipLv_;
    }
    public Builder setSenderVipLv(int value) {
      bitField0_ |= 0x00000400;
      senderVipLv_ = value;
      onChanged();
      return this;
    }
    public Builder clearSenderVipLv() {
      bitField0_ = (bitField0_ & ~0x00000400);
      senderVipLv_ = 0;
      onChanged();
      return this;
    }
    
    // @@protoc_insertion_point(builder_scope:com.hpw.net.msg.NetMail)
  }
  
  static {
    defaultInstance = new NetMail(true);
    defaultInstance.initFields();
  }
  
  // @@protoc_insertion_point(class_scope:com.hpw.net.msg.NetMail)
}

