/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.shoes.ordering.system.common.kafka.model;

import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.SchemaStore;
import org.apache.avro.specific.SpecificData;

@org.apache.avro.specific.AvroGenerated
public class UpdateMemberResponseAvroModel extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -94028693850493557L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"UpdateMemberResponseAvroModel\",\"namespace\":\"com.shoes.ordering.system\",\"fields\":[{\"name\":\"productId\",\"type\":{\"type\":\"string\",\"logicalType\":\"uuid\"}},{\"name\":\"memberStatus\",\"type\":{\"type\":\"enum\",\"name\":\"MemberStatus\",\"symbols\":[\"PENDING\",\"ACTIVATE\",\"DEACTIVATE\",\"WITHDRAWAL\"]}},{\"name\":\"message\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}},{\"name\":\"createdAt\",\"type\":{\"type\":\"long\",\"logicalType\":\"timestamp-millis\"}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();
  static {
    MODEL$.addLogicalTypeConversion(new org.apache.avro.Conversions.UUIDConversion());
    MODEL$.addLogicalTypeConversion(new org.apache.avro.data.TimeConversions.TimestampMillisConversion());
  }

  private static final BinaryMessageEncoder<UpdateMemberResponseAvroModel> ENCODER =
      new BinaryMessageEncoder<>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<UpdateMemberResponseAvroModel> DECODER =
      new BinaryMessageDecoder<>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<UpdateMemberResponseAvroModel> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<UpdateMemberResponseAvroModel> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<UpdateMemberResponseAvroModel> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this UpdateMemberResponseAvroModel to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a UpdateMemberResponseAvroModel from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a UpdateMemberResponseAvroModel instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static UpdateMemberResponseAvroModel fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private java.util.UUID productId;
  private MemberStatus memberStatus;
  private java.util.List<String> message;
  private java.time.Instant createdAt;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public UpdateMemberResponseAvroModel() {}

  /**
   * All-args constructor.
   * @param productId The new value for productId
   * @param memberStatus The new value for memberStatus
   * @param message The new value for message
   * @param createdAt The new value for createdAt
   */
  public UpdateMemberResponseAvroModel(java.util.UUID productId, MemberStatus memberStatus, java.util.List<String> message, java.time.Instant createdAt) {
    this.productId = productId;
    this.memberStatus = memberStatus;
    this.message = message;
    this.createdAt = createdAt.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
  }

  @Override
  public SpecificData getSpecificData() { return MODEL$; }

  @Override
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }

  // Used by DatumWriter.  Applications should not call.
  @Override
  public Object get(int field$) {
    switch (field$) {
    case 0: return productId;
    case 1: return memberStatus;
    case 2: return message;
    case 3: return createdAt;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  private static final org.apache.avro.Conversion<?>[] conversions =
      new org.apache.avro.Conversion<?>[] {
      new org.apache.avro.Conversions.UUIDConversion(),
      null,
      null,
      new org.apache.avro.data.TimeConversions.TimestampMillisConversion(),
      null
  };

  @Override
  public org.apache.avro.Conversion<?> getConversion(int field) {
    return conversions[field];
  }

  // Used by DatumReader.  Applications should not call.
  @Override
  @SuppressWarnings(value="unchecked")
  public void put(int field$, Object value$) {
    switch (field$) {
    case 0: productId = (java.util.UUID)value$; break;
    case 1: memberStatus = (MemberStatus)value$; break;
    case 2: message = (java.util.List<String>)value$; break;
    case 3: createdAt = (java.time.Instant)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'productId' field.
   * @return The value of the 'productId' field.
   */
  public java.util.UUID getProductId() {
    return productId;
  }


  /**
   * Sets the value of the 'productId' field.
   * @param value the value to set.
   */
  public void setProductId(java.util.UUID value) {
    this.productId = value;
  }

  /**
   * Gets the value of the 'memberStatus' field.
   * @return The value of the 'memberStatus' field.
   */
  public MemberStatus getMemberStatus() {
    return memberStatus;
  }


  /**
   * Sets the value of the 'memberStatus' field.
   * @param value the value to set.
   */
  public void setMemberStatus(MemberStatus value) {
    this.memberStatus = value;
  }

  /**
   * Gets the value of the 'message' field.
   * @return The value of the 'message' field.
   */
  public java.util.List<String> getMessage() {
    return message;
  }


  /**
   * Sets the value of the 'message' field.
   * @param value the value to set.
   */
  public void setMessage(java.util.List<String> value) {
    this.message = value;
  }

  /**
   * Gets the value of the 'createdAt' field.
   * @return The value of the 'createdAt' field.
   */
  public java.time.Instant getCreatedAt() {
    return createdAt;
  }


  /**
   * Sets the value of the 'createdAt' field.
   * @param value the value to set.
   */
  public void setCreatedAt(java.time.Instant value) {
    this.createdAt = value.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
  }

  /**
   * Creates a new UpdateMemberResponseAvroModel RecordBuilder.
   * @return A new UpdateMemberResponseAvroModel RecordBuilder
   */
  public static UpdateMemberResponseAvroModel.Builder newBuilder() {
    return new UpdateMemberResponseAvroModel.Builder();
  }

  /**
   * Creates a new UpdateMemberResponseAvroModel RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new UpdateMemberResponseAvroModel RecordBuilder
   */
  public static UpdateMemberResponseAvroModel.Builder newBuilder(UpdateMemberResponseAvroModel.Builder other) {
    if (other == null) {
      return new UpdateMemberResponseAvroModel.Builder();
    } else {
      return new UpdateMemberResponseAvroModel.Builder(other);
    }
  }

  /**
   * Creates a new UpdateMemberResponseAvroModel RecordBuilder by copying an existing UpdateMemberResponseAvroModel instance.
   * @param other The existing instance to copy.
   * @return A new UpdateMemberResponseAvroModel RecordBuilder
   */
  public static UpdateMemberResponseAvroModel.Builder newBuilder(UpdateMemberResponseAvroModel other) {
    if (other == null) {
      return new UpdateMemberResponseAvroModel.Builder();
    } else {
      return new UpdateMemberResponseAvroModel.Builder(other);
    }
  }

  /**
   * RecordBuilder for UpdateMemberResponseAvroModel instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<UpdateMemberResponseAvroModel>
    implements org.apache.avro.data.RecordBuilder<UpdateMemberResponseAvroModel> {

    private java.util.UUID productId;
    private MemberStatus memberStatus;
    private java.util.List<String> message;
    private java.time.Instant createdAt;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(UpdateMemberResponseAvroModel.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.productId)) {
        this.productId = data().deepCopy(fields()[0].schema(), other.productId);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.memberStatus)) {
        this.memberStatus = data().deepCopy(fields()[1].schema(), other.memberStatus);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.message)) {
        this.message = data().deepCopy(fields()[2].schema(), other.message);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.createdAt)) {
        this.createdAt = data().deepCopy(fields()[3].schema(), other.createdAt);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
    }

    /**
     * Creates a Builder by copying an existing UpdateMemberResponseAvroModel instance
     * @param other The existing instance to copy.
     */
    private Builder(UpdateMemberResponseAvroModel other) {
      super(SCHEMA$, MODEL$);
      if (isValidValue(fields()[0], other.productId)) {
        this.productId = data().deepCopy(fields()[0].schema(), other.productId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.memberStatus)) {
        this.memberStatus = data().deepCopy(fields()[1].schema(), other.memberStatus);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.message)) {
        this.message = data().deepCopy(fields()[2].schema(), other.message);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.createdAt)) {
        this.createdAt = data().deepCopy(fields()[3].schema(), other.createdAt);
        fieldSetFlags()[3] = true;
      }
    }

    /**
      * Gets the value of the 'productId' field.
      * @return The value.
      */
    public java.util.UUID getProductId() {
      return productId;
    }


    /**
      * Sets the value of the 'productId' field.
      * @param value The value of 'productId'.
      * @return This builder.
      */
    public UpdateMemberResponseAvroModel.Builder setProductId(java.util.UUID value) {
      validate(fields()[0], value);
      this.productId = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'productId' field has been set.
      * @return True if the 'productId' field has been set, false otherwise.
      */
    public boolean hasProductId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'productId' field.
      * @return This builder.
      */
    public UpdateMemberResponseAvroModel.Builder clearProductId() {
      productId = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'memberStatus' field.
      * @return The value.
      */
    public MemberStatus getMemberStatus() {
      return memberStatus;
    }


    /**
      * Sets the value of the 'memberStatus' field.
      * @param value The value of 'memberStatus'.
      * @return This builder.
      */
    public UpdateMemberResponseAvroModel.Builder setMemberStatus(MemberStatus value) {
      validate(fields()[1], value);
      this.memberStatus = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'memberStatus' field has been set.
      * @return True if the 'memberStatus' field has been set, false otherwise.
      */
    public boolean hasMemberStatus() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'memberStatus' field.
      * @return This builder.
      */
    public UpdateMemberResponseAvroModel.Builder clearMemberStatus() {
      memberStatus = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'message' field.
      * @return The value.
      */
    public java.util.List<String> getMessage() {
      return message;
    }


    /**
      * Sets the value of the 'message' field.
      * @param value The value of 'message'.
      * @return This builder.
      */
    public UpdateMemberResponseAvroModel.Builder setMessage(java.util.List<String> value) {
      validate(fields()[2], value);
      this.message = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'message' field has been set.
      * @return True if the 'message' field has been set, false otherwise.
      */
    public boolean hasMessage() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'message' field.
      * @return This builder.
      */
    public UpdateMemberResponseAvroModel.Builder clearMessage() {
      message = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'createdAt' field.
      * @return The value.
      */
    public java.time.Instant getCreatedAt() {
      return createdAt;
    }


    /**
      * Sets the value of the 'createdAt' field.
      * @param value The value of 'createdAt'.
      * @return This builder.
      */
    public UpdateMemberResponseAvroModel.Builder setCreatedAt(java.time.Instant value) {
      validate(fields()[3], value);
      this.createdAt = value.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'createdAt' field has been set.
      * @return True if the 'createdAt' field has been set, false otherwise.
      */
    public boolean hasCreatedAt() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'createdAt' field.
      * @return This builder.
      */
    public UpdateMemberResponseAvroModel.Builder clearCreatedAt() {
      fieldSetFlags()[3] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public UpdateMemberResponseAvroModel build() {
      try {
        UpdateMemberResponseAvroModel record = new UpdateMemberResponseAvroModel();
        record.productId = fieldSetFlags()[0] ? this.productId : (java.util.UUID) defaultValue(fields()[0]);
        record.memberStatus = fieldSetFlags()[1] ? this.memberStatus : (MemberStatus) defaultValue(fields()[1]);
        record.message = fieldSetFlags()[2] ? this.message : (java.util.List<String>) defaultValue(fields()[2]);
        record.createdAt = fieldSetFlags()[3] ? this.createdAt : (java.time.Instant) defaultValue(fields()[3]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<UpdateMemberResponseAvroModel>
    WRITER$ = (org.apache.avro.io.DatumWriter<UpdateMemberResponseAvroModel>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<UpdateMemberResponseAvroModel>
    READER$ = (org.apache.avro.io.DatumReader<UpdateMemberResponseAvroModel>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}










