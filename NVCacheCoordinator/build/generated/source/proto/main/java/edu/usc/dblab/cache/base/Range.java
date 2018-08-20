// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: nvcache.proto

package edu.usc.dblab.cache.base;

/**
 * Protobuf type {@code nvcache.Range}
 */
public  final class Range extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:nvcache.Range)
    RangeOrBuilder {
  // Use Range.newBuilder() to construct.
  private Range(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Range() {
    start_ = 0;
    end_ = 0;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private Range(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    int mutable_bitField0_ = 0;
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!input.skipField(tag)) {
              done = true;
            }
            break;
          }
          case 8: {

            start_ = input.readInt32();
            break;
          }
          case 16: {

            end_ = input.readInt32();
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return edu.usc.dblab.cache.base.CacheProto.internal_static_nvcache_Range_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return edu.usc.dblab.cache.base.CacheProto.internal_static_nvcache_Range_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            edu.usc.dblab.cache.base.Range.class, edu.usc.dblab.cache.base.Range.Builder.class);
  }

  public static final int START_FIELD_NUMBER = 1;
  private int start_;
  /**
   * <code>int32 start = 1;</code>
   */
  public int getStart() {
    return start_;
  }

  public static final int END_FIELD_NUMBER = 2;
  private int end_;
  /**
   * <code>int32 end = 2;</code>
   */
  public int getEnd() {
    return end_;
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (start_ != 0) {
      output.writeInt32(1, start_);
    }
    if (end_ != 0) {
      output.writeInt32(2, end_);
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (start_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, start_);
    }
    if (end_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(2, end_);
    }
    memoizedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof edu.usc.dblab.cache.base.Range)) {
      return super.equals(obj);
    }
    edu.usc.dblab.cache.base.Range other = (edu.usc.dblab.cache.base.Range) obj;

    boolean result = true;
    result = result && (getStart()
        == other.getStart());
    result = result && (getEnd()
        == other.getEnd());
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + START_FIELD_NUMBER;
    hash = (53 * hash) + getStart();
    hash = (37 * hash) + END_FIELD_NUMBER;
    hash = (53 * hash) + getEnd();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static edu.usc.dblab.cache.base.Range parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static edu.usc.dblab.cache.base.Range parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static edu.usc.dblab.cache.base.Range parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static edu.usc.dblab.cache.base.Range parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static edu.usc.dblab.cache.base.Range parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static edu.usc.dblab.cache.base.Range parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static edu.usc.dblab.cache.base.Range parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static edu.usc.dblab.cache.base.Range parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static edu.usc.dblab.cache.base.Range parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static edu.usc.dblab.cache.base.Range parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static edu.usc.dblab.cache.base.Range parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static edu.usc.dblab.cache.base.Range parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(edu.usc.dblab.cache.base.Range prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code nvcache.Range}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:nvcache.Range)
      edu.usc.dblab.cache.base.RangeOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return edu.usc.dblab.cache.base.CacheProto.internal_static_nvcache_Range_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return edu.usc.dblab.cache.base.CacheProto.internal_static_nvcache_Range_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              edu.usc.dblab.cache.base.Range.class, edu.usc.dblab.cache.base.Range.Builder.class);
    }

    // Construct using edu.usc.dblab.cache.base.Range.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    public Builder clear() {
      super.clear();
      start_ = 0;

      end_ = 0;

      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return edu.usc.dblab.cache.base.CacheProto.internal_static_nvcache_Range_descriptor;
    }

    public edu.usc.dblab.cache.base.Range getDefaultInstanceForType() {
      return edu.usc.dblab.cache.base.Range.getDefaultInstance();
    }

    public edu.usc.dblab.cache.base.Range build() {
      edu.usc.dblab.cache.base.Range result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public edu.usc.dblab.cache.base.Range buildPartial() {
      edu.usc.dblab.cache.base.Range result = new edu.usc.dblab.cache.base.Range(this);
      result.start_ = start_;
      result.end_ = end_;
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof edu.usc.dblab.cache.base.Range) {
        return mergeFrom((edu.usc.dblab.cache.base.Range)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(edu.usc.dblab.cache.base.Range other) {
      if (other == edu.usc.dblab.cache.base.Range.getDefaultInstance()) return this;
      if (other.getStart() != 0) {
        setStart(other.getStart());
      }
      if (other.getEnd() != 0) {
        setEnd(other.getEnd());
      }
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      edu.usc.dblab.cache.base.Range parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (edu.usc.dblab.cache.base.Range) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int start_ ;
    /**
     * <code>int32 start = 1;</code>
     */
    public int getStart() {
      return start_;
    }
    /**
     * <code>int32 start = 1;</code>
     */
    public Builder setStart(int value) {
      
      start_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 start = 1;</code>
     */
    public Builder clearStart() {
      
      start_ = 0;
      onChanged();
      return this;
    }

    private int end_ ;
    /**
     * <code>int32 end = 2;</code>
     */
    public int getEnd() {
      return end_;
    }
    /**
     * <code>int32 end = 2;</code>
     */
    public Builder setEnd(int value) {
      
      end_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 end = 2;</code>
     */
    public Builder clearEnd() {
      
      end_ = 0;
      onChanged();
      return this;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:nvcache.Range)
  }

  // @@protoc_insertion_point(class_scope:nvcache.Range)
  private static final edu.usc.dblab.cache.base.Range DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new edu.usc.dblab.cache.base.Range();
  }

  public static edu.usc.dblab.cache.base.Range getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Range>
      PARSER = new com.google.protobuf.AbstractParser<Range>() {
    public Range parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new Range(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<Range> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Range> getParserForType() {
    return PARSER;
  }

  public edu.usc.dblab.cache.base.Range getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

