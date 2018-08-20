// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: nvcache.proto

package edu.usc.dblab.cache.base;

public final class CacheProto {
  private CacheProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_nvcache_Configuration_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_nvcache_Configuration_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_nvcache_ReadConfigRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_nvcache_ReadConfigRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_nvcache_ReadConfigResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_nvcache_ReadConfigResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_nvcache_Fragment_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_nvcache_Fragment_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_nvcache_Range_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_nvcache_Range_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_nvcache_AssignmentConfiguration_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_nvcache_AssignmentConfiguration_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_nvcache_AssignmentConfiguration_ServerStateMapEntry_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_nvcache_AssignmentConfiguration_ServerStateMapEntry_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_nvcache_WorkloadConfiguration_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_nvcache_WorkloadConfiguration_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_nvcache_HelloRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_nvcache_HelloRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_nvcache_HelloReply_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_nvcache_HelloReply_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\rnvcache.proto\022\007nvcache\"7\n\rConfiguratio" +
      "n\022\025\n\rconfig_number\030\001 \001(\005\022\017\n\007servers\030\002 \003(" +
      "\t\"\023\n\021ReadConfigRequest\"\177\n\022ReadConfigResp" +
      "onse\0227\n\rconfiguration\030\001 \001(\0132 .nvcache.As" +
      "signmentConfiguration\0220\n\010workload\030\002 \001(\0132" +
      "\036.nvcache.WorkloadConfiguration\"\261\002\n\010Frag" +
      "ment\022\027\n\017physical_server\030\001 \001(\t\022\026\n\016migrate" +
      "_server\030\002 \001(\t\022\023\n\013fragment_id\030\003 \001(\005\022\034\n\024pr" +
      "ev_fragment_cfg_id\030\004 \001(\005\022\027\n\017fragment_cfg" +
      "_id\030\005 \001(\005\022\034\n\024next_fragment_cfg_id\030\006 \001(\005\022",
      ".\n\005state\030\007 \001(\0162\037.nvcache.Fragment.Fragme" +
      "ntState\022\021\n\tfrequency\030\010 \001(\001\022\035\n\005range\030\t \003(" +
      "\0132\016.nvcache.Range\"(\n\rFragmentState\022\n\n\006NO" +
      "RMAL\020\000\022\013\n\007MIGRATE\020\001\"#\n\005Range\022\r\n\005start\030\001 " +
      "\001(\005\022\013\n\003end\030\002 \001(\005\"\265\002\n\027AssignmentConfigura" +
      "tion\022\025\n\rconfig_number\030\001 \001(\005\022$\n\tfragments" +
      "\030\002 \003(\0132\021.nvcache.Fragment\022N\n\020server_stat" +
      "e_map\030\003 \003(\01324.nvcache.AssignmentConfigur" +
      "ation.ServerStateMapEntry\032c\n\023ServerState" +
      "MapEntry\022\013\n\003key\030\001 \001(\t\022;\n\005value\030\002 \001(\0162,.n",
      "vcache.AssignmentConfiguration.ServerSta" +
      "te:\0028\001\"(\n\013ServerState\022\n\n\006NORMAL\020\000\022\r\n\tMIG" +
      "RATION\020\001\"\222\002\n\025WorkloadConfiguration\022\025\n\rco" +
      "nfig_number\030\001 \001(\005\0229\n\010workload\030\002 \001(\0162\'.nv" +
      "cache.WorkloadConfiguration.Workload\022\014\n\004" +
      "base\030\003 \001(\005\022\013\n\003max\030\004 \001(\005\022\016\n\006param1\030\005 \001(\005\022" +
      "\016\n\006param2\030\006 \001(\005\022$\n\tfragments\030\007 \003(\0132\021.nvc" +
      "ache.Fragment\"F\n\010Workload\022\n\n\006NORMAL\020\000\022\n\n" +
      "\006HOT_IN\020\001\022\013\n\007HOT_OUT\020\002\022\n\n\006RANDOM\020\003\022\t\n\005SH" +
      "IFT\020\004\"\034\n\014HelloRequest\022\014\n\004name\030\001 \001(\t\"\035\n\nH",
      "elloReply\022\017\n\007message\030\001 \001(\t2\227\001\n\022NVCacheCo" +
      "ordinator\0228\n\010SayHello\022\025.nvcache.HelloReq" +
      "uest\032\023.nvcache.HelloReply\"\000\022G\n\nReadConfi" +
      "g\022\032.nvcache.ReadConfigRequest\032\033.nvcache." +
      "ReadConfigResponse\"\000B0\n\030edu.usc.dblab.ca" +
      "che.baseB\nCacheProtoP\001\242\002\005Cacheb\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_nvcache_Configuration_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_nvcache_Configuration_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_nvcache_Configuration_descriptor,
        new java.lang.String[] { "ConfigNumber", "Servers", });
    internal_static_nvcache_ReadConfigRequest_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_nvcache_ReadConfigRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_nvcache_ReadConfigRequest_descriptor,
        new java.lang.String[] { });
    internal_static_nvcache_ReadConfigResponse_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_nvcache_ReadConfigResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_nvcache_ReadConfigResponse_descriptor,
        new java.lang.String[] { "Configuration", "Workload", });
    internal_static_nvcache_Fragment_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_nvcache_Fragment_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_nvcache_Fragment_descriptor,
        new java.lang.String[] { "PhysicalServer", "MigrateServer", "FragmentId", "PrevFragmentCfgId", "FragmentCfgId", "NextFragmentCfgId", "State", "Frequency", "Range", });
    internal_static_nvcache_Range_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_nvcache_Range_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_nvcache_Range_descriptor,
        new java.lang.String[] { "Start", "End", });
    internal_static_nvcache_AssignmentConfiguration_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_nvcache_AssignmentConfiguration_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_nvcache_AssignmentConfiguration_descriptor,
        new java.lang.String[] { "ConfigNumber", "Fragments", "ServerStateMap", });
    internal_static_nvcache_AssignmentConfiguration_ServerStateMapEntry_descriptor =
      internal_static_nvcache_AssignmentConfiguration_descriptor.getNestedTypes().get(0);
    internal_static_nvcache_AssignmentConfiguration_ServerStateMapEntry_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_nvcache_AssignmentConfiguration_ServerStateMapEntry_descriptor,
        new java.lang.String[] { "Key", "Value", });
    internal_static_nvcache_WorkloadConfiguration_descriptor =
      getDescriptor().getMessageTypes().get(6);
    internal_static_nvcache_WorkloadConfiguration_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_nvcache_WorkloadConfiguration_descriptor,
        new java.lang.String[] { "ConfigNumber", "Workload", "Base", "Max", "Param1", "Param2", "Fragments", });
    internal_static_nvcache_HelloRequest_descriptor =
      getDescriptor().getMessageTypes().get(7);
    internal_static_nvcache_HelloRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_nvcache_HelloRequest_descriptor,
        new java.lang.String[] { "Name", });
    internal_static_nvcache_HelloReply_descriptor =
      getDescriptor().getMessageTypes().get(8);
    internal_static_nvcache_HelloReply_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_nvcache_HelloReply_descriptor,
        new java.lang.String[] { "Message", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
