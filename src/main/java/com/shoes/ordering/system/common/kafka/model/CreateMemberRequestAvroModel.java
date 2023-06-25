/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.shoes.ordering.system.common.kafka.model;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class CreateMemberRequestAvroModel extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 8094690200880879810L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"CreateMemberRequestAvroModel\",\"namespace\":\"com.shoes.ordering.system\",\"fields\":[{\"name\":\"memberId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"logicalType\":\"uuid\"},{\"name\":\"name\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"password\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"email\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"memberKind\",\"type\":{\"type\":\"enum\",\"name\":\"MemberKind\",\"symbols\":[\"CUSTOMER\",\"SELLER\",\"ADMIN\"]}},{\"name\":\"memberStatus\",\"type\":{\"type\":\"enum\",\"name\":\"MemberStatus\",\"symbols\":[\"PENDING\",\"ACTIVATE\",\"DEACTIVATE\",\"WITHDRAWAL\"]}},{\"name\":\"phoneNumber\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"address\",\"type\":{\"type\":\"record\",\"name\":\"MemberAddress\",\"fields\":[{\"name\":\"street\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"city\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"state\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"postalCode\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}},{\"name\":\"createdAt\",\"type\":{\"type\":\"long\",\"logicalType\":\"timestamp-millis\"}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();
  static {
    MODEL$.addLogicalTypeConversion(new org.apache.avro.data.TimeConversions.TimestampMillisConversion());
  }

  private static final BinaryMessageEncoder<CreateMemberRequestAvroModel> ENCODER =
      new BinaryMessageEncoder<>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<CreateMemberRequestAvroModel> DECODER =
      new BinaryMessageDecoder<>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<CreateMemberRequestAvroModel> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<CreateMemberRequestAvroModel> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<CreateMemberRequestAvroModel> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this CreateMemberRequestAvroModel to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a CreateMemberRequestAvroModel from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a CreateMemberRequestAvroModel instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static CreateMemberRequestAvroModel fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private java.lang.String memberId;
  private java.lang.String name;
  private java.lang.String password;
  private java.lang.String email;
  private MemberKind memberKind;
  private MemberStatus memberStatus;
  private java.lang.String phoneNumber;
  private MemberAddress address;
  private java.time.Instant createdAt;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public CreateMemberRequestAvroModel() {}

  /**
   * All-args constructor.
   * @param memberId The new value for memberId
   * @param name The new value for name
   * @param password The new value for password
   * @param email The new value for email
   * @param memberKind The new value for memberKind
   * @param memberStatus The new value for memberStatus
   * @param phoneNumber The new value for phoneNumber
   * @param address The new value for address
   * @param createdAt The new value for createdAt
   */
  public CreateMemberRequestAvroModel(java.lang.String memberId, java.lang.String name, java.lang.String password, java.lang.String email, MemberKind memberKind, MemberStatus memberStatus, java.lang.String phoneNumber, MemberAddress address, java.time.Instant createdAt) {
    this.memberId = memberId;
    this.name = name;
    this.password = password;
    this.email = email;
    this.memberKind = memberKind;
    this.memberStatus = memberStatus;
    this.phoneNumber = phoneNumber;
    this.address = address;
    this.createdAt = createdAt.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
  }

  @Override
  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }

  @Override
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }

  // Used by DatumWriter.  Applications should not call.
  @Override
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return memberId;
    case 1: return name;
    case 2: return password;
    case 3: return email;
    case 4: return memberKind;
    case 5: return memberStatus;
    case 6: return phoneNumber;
    case 7: return address;
    case 8: return createdAt;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  private static final org.apache.avro.Conversion<?>[] conversions =
      new org.apache.avro.Conversion<?>[] {
      null,
      null,
      null,
      null,
      null,
      null,
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
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: memberId = value$ != null ? value$.toString() : null; break;
    case 1: name = value$ != null ? value$.toString() : null; break;
    case 2: password = value$ != null ? value$.toString() : null; break;
    case 3: email = value$ != null ? value$.toString() : null; break;
    case 4: memberKind = (MemberKind)value$; break;
    case 5: memberStatus = (MemberStatus)value$; break;
    case 6: phoneNumber = value$ != null ? value$.toString() : null; break;
    case 7: address = (MemberAddress)value$; break;
    case 8: createdAt = (java.time.Instant)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'memberId' field.
   * @return The value of the 'memberId' field.
   */
  public java.lang.String getMemberId() {
    return memberId;
  }


  /**
   * Sets the value of the 'memberId' field.
   * @param value the value to set.
   */
  public void setMemberId(java.lang.String value) {
    this.memberId = value;
  }

  /**
   * Gets the value of the 'name' field.
   * @return The value of the 'name' field.
   */
  public java.lang.String getName() {
    return name;
  }


  /**
   * Sets the value of the 'name' field.
   * @param value the value to set.
   */
  public void setName(java.lang.String value) {
    this.name = value;
  }

  /**
   * Gets the value of the 'password' field.
   * @return The value of the 'password' field.
   */
  public java.lang.String getPassword() {
    return password;
  }


  /**
   * Sets the value of the 'password' field.
   * @param value the value to set.
   */
  public void setPassword(java.lang.String value) {
    this.password = value;
  }

  /**
   * Gets the value of the 'email' field.
   * @return The value of the 'email' field.
   */
  public java.lang.String getEmail() {
    return email;
  }


  /**
   * Sets the value of the 'email' field.
   * @param value the value to set.
   */
  public void setEmail(java.lang.String value) {
    this.email = value;
  }

  /**
   * Gets the value of the 'memberKind' field.
   * @return The value of the 'memberKind' field.
   */
  public MemberKind getMemberKind() {
    return memberKind;
  }


  /**
   * Sets the value of the 'memberKind' field.
   * @param value the value to set.
   */
  public void setMemberKind(MemberKind value) {
    this.memberKind = value;
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
   * Gets the value of the 'phoneNumber' field.
   * @return The value of the 'phoneNumber' field.
   */
  public java.lang.String getPhoneNumber() {
    return phoneNumber;
  }


  /**
   * Sets the value of the 'phoneNumber' field.
   * @param value the value to set.
   */
  public void setPhoneNumber(java.lang.String value) {
    this.phoneNumber = value;
  }

  /**
   * Gets the value of the 'address' field.
   * @return The value of the 'address' field.
   */
  public MemberAddress getAddress() {
    return address;
  }


  /**
   * Sets the value of the 'address' field.
   * @param value the value to set.
   */
  public void setAddress(MemberAddress value) {
    this.address = value;
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
   * Creates a new CreateMemberRequestAvroModel RecordBuilder.
   * @return A new CreateMemberRequestAvroModel RecordBuilder
   */
  public static CreateMemberRequestAvroModel.Builder newBuilder() {
    return new CreateMemberRequestAvroModel.Builder();
  }

  /**
   * Creates a new CreateMemberRequestAvroModel RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new CreateMemberRequestAvroModel RecordBuilder
   */
  public static CreateMemberRequestAvroModel.Builder newBuilder(CreateMemberRequestAvroModel.Builder other) {
    if (other == null) {
      return new CreateMemberRequestAvroModel.Builder();
    } else {
      return new CreateMemberRequestAvroModel.Builder(other);
    }
  }

  /**
   * Creates a new CreateMemberRequestAvroModel RecordBuilder by copying an existing CreateMemberRequestAvroModel instance.
   * @param other The existing instance to copy.
   * @return A new CreateMemberRequestAvroModel RecordBuilder
   */
  public static CreateMemberRequestAvroModel.Builder newBuilder(CreateMemberRequestAvroModel other) {
    if (other == null) {
      return new CreateMemberRequestAvroModel.Builder();
    } else {
      return new CreateMemberRequestAvroModel.Builder(other);
    }
  }

  /**
   * RecordBuilder for CreateMemberRequestAvroModel instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<CreateMemberRequestAvroModel>
    implements org.apache.avro.data.RecordBuilder<CreateMemberRequestAvroModel> {

    private java.lang.String memberId;
    private java.lang.String name;
    private java.lang.String password;
    private java.lang.String email;
    private MemberKind memberKind;
    private MemberStatus memberStatus;
    private java.lang.String phoneNumber;
    private MemberAddress address;
    private MemberAddress.Builder addressBuilder;
    private java.time.Instant createdAt;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(CreateMemberRequestAvroModel.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.memberId)) {
        this.memberId = data().deepCopy(fields()[0].schema(), other.memberId);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.name)) {
        this.name = data().deepCopy(fields()[1].schema(), other.name);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.password)) {
        this.password = data().deepCopy(fields()[2].schema(), other.password);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.email)) {
        this.email = data().deepCopy(fields()[3].schema(), other.email);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
      if (isValidValue(fields()[4], other.memberKind)) {
        this.memberKind = data().deepCopy(fields()[4].schema(), other.memberKind);
        fieldSetFlags()[4] = other.fieldSetFlags()[4];
      }
      if (isValidValue(fields()[5], other.memberStatus)) {
        this.memberStatus = data().deepCopy(fields()[5].schema(), other.memberStatus);
        fieldSetFlags()[5] = other.fieldSetFlags()[5];
      }
      if (isValidValue(fields()[6], other.phoneNumber)) {
        this.phoneNumber = data().deepCopy(fields()[6].schema(), other.phoneNumber);
        fieldSetFlags()[6] = other.fieldSetFlags()[6];
      }
      if (isValidValue(fields()[7], other.address)) {
        this.address = data().deepCopy(fields()[7].schema(), other.address);
        fieldSetFlags()[7] = other.fieldSetFlags()[7];
      }
      if (other.hasAddressBuilder()) {
        this.addressBuilder = MemberAddress.newBuilder(other.getAddressBuilder());
      }
      if (isValidValue(fields()[8], other.createdAt)) {
        this.createdAt = data().deepCopy(fields()[8].schema(), other.createdAt);
        fieldSetFlags()[8] = other.fieldSetFlags()[8];
      }
    }

    /**
     * Creates a Builder by copying an existing CreateMemberRequestAvroModel instance
     * @param other The existing instance to copy.
     */
    private Builder(CreateMemberRequestAvroModel other) {
      super(SCHEMA$, MODEL$);
      if (isValidValue(fields()[0], other.memberId)) {
        this.memberId = data().deepCopy(fields()[0].schema(), other.memberId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.name)) {
        this.name = data().deepCopy(fields()[1].schema(), other.name);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.password)) {
        this.password = data().deepCopy(fields()[2].schema(), other.password);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.email)) {
        this.email = data().deepCopy(fields()[3].schema(), other.email);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.memberKind)) {
        this.memberKind = data().deepCopy(fields()[4].schema(), other.memberKind);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.memberStatus)) {
        this.memberStatus = data().deepCopy(fields()[5].schema(), other.memberStatus);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.phoneNumber)) {
        this.phoneNumber = data().deepCopy(fields()[6].schema(), other.phoneNumber);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.address)) {
        this.address = data().deepCopy(fields()[7].schema(), other.address);
        fieldSetFlags()[7] = true;
      }
      this.addressBuilder = null;
      if (isValidValue(fields()[8], other.createdAt)) {
        this.createdAt = data().deepCopy(fields()[8].schema(), other.createdAt);
        fieldSetFlags()[8] = true;
      }
    }

    /**
      * Gets the value of the 'memberId' field.
      * @return The value.
      */
    public java.lang.String getMemberId() {
      return memberId;
    }


    /**
      * Sets the value of the 'memberId' field.
      * @param value The value of 'memberId'.
      * @return This builder.
      */
    public CreateMemberRequestAvroModel.Builder setMemberId(java.lang.String value) {
      validate(fields()[0], value);
      this.memberId = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'memberId' field has been set.
      * @return True if the 'memberId' field has been set, false otherwise.
      */
    public boolean hasMemberId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'memberId' field.
      * @return This builder.
      */
    public CreateMemberRequestAvroModel.Builder clearMemberId() {
      memberId = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'name' field.
      * @return The value.
      */
    public java.lang.String getName() {
      return name;
    }


    /**
      * Sets the value of the 'name' field.
      * @param value The value of 'name'.
      * @return This builder.
      */
    public CreateMemberRequestAvroModel.Builder setName(java.lang.String value) {
      validate(fields()[1], value);
      this.name = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'name' field has been set.
      * @return True if the 'name' field has been set, false otherwise.
      */
    public boolean hasName() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'name' field.
      * @return This builder.
      */
    public CreateMemberRequestAvroModel.Builder clearName() {
      name = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'password' field.
      * @return The value.
      */
    public java.lang.String getPassword() {
      return password;
    }


    /**
      * Sets the value of the 'password' field.
      * @param value The value of 'password'.
      * @return This builder.
      */
    public CreateMemberRequestAvroModel.Builder setPassword(java.lang.String value) {
      validate(fields()[2], value);
      this.password = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'password' field has been set.
      * @return True if the 'password' field has been set, false otherwise.
      */
    public boolean hasPassword() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'password' field.
      * @return This builder.
      */
    public CreateMemberRequestAvroModel.Builder clearPassword() {
      password = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'email' field.
      * @return The value.
      */
    public java.lang.String getEmail() {
      return email;
    }


    /**
      * Sets the value of the 'email' field.
      * @param value The value of 'email'.
      * @return This builder.
      */
    public CreateMemberRequestAvroModel.Builder setEmail(java.lang.String value) {
      validate(fields()[3], value);
      this.email = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'email' field has been set.
      * @return True if the 'email' field has been set, false otherwise.
      */
    public boolean hasEmail() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'email' field.
      * @return This builder.
      */
    public CreateMemberRequestAvroModel.Builder clearEmail() {
      email = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'memberKind' field.
      * @return The value.
      */
    public MemberKind getMemberKind() {
      return memberKind;
    }


    /**
      * Sets the value of the 'memberKind' field.
      * @param value The value of 'memberKind'.
      * @return This builder.
      */
    public CreateMemberRequestAvroModel.Builder setMemberKind(MemberKind value) {
      validate(fields()[4], value);
      this.memberKind = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'memberKind' field has been set.
      * @return True if the 'memberKind' field has been set, false otherwise.
      */
    public boolean hasMemberKind() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'memberKind' field.
      * @return This builder.
      */
    public CreateMemberRequestAvroModel.Builder clearMemberKind() {
      memberKind = null;
      fieldSetFlags()[4] = false;
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
    public CreateMemberRequestAvroModel.Builder setMemberStatus(MemberStatus value) {
      validate(fields()[5], value);
      this.memberStatus = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'memberStatus' field has been set.
      * @return True if the 'memberStatus' field has been set, false otherwise.
      */
    public boolean hasMemberStatus() {
      return fieldSetFlags()[5];
    }


    /**
      * Clears the value of the 'memberStatus' field.
      * @return This builder.
      */
    public CreateMemberRequestAvroModel.Builder clearMemberStatus() {
      memberStatus = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    /**
      * Gets the value of the 'phoneNumber' field.
      * @return The value.
      */
    public java.lang.String getPhoneNumber() {
      return phoneNumber;
    }


    /**
      * Sets the value of the 'phoneNumber' field.
      * @param value The value of 'phoneNumber'.
      * @return This builder.
      */
    public CreateMemberRequestAvroModel.Builder setPhoneNumber(java.lang.String value) {
      validate(fields()[6], value);
      this.phoneNumber = value;
      fieldSetFlags()[6] = true;
      return this;
    }

    /**
      * Checks whether the 'phoneNumber' field has been set.
      * @return True if the 'phoneNumber' field has been set, false otherwise.
      */
    public boolean hasPhoneNumber() {
      return fieldSetFlags()[6];
    }


    /**
      * Clears the value of the 'phoneNumber' field.
      * @return This builder.
      */
    public CreateMemberRequestAvroModel.Builder clearPhoneNumber() {
      phoneNumber = null;
      fieldSetFlags()[6] = false;
      return this;
    }

    /**
      * Gets the value of the 'address' field.
      * @return The value.
      */
    public MemberAddress getAddress() {
      return address;
    }


    /**
      * Sets the value of the 'address' field.
      * @param value The value of 'address'.
      * @return This builder.
      */
    public CreateMemberRequestAvroModel.Builder setAddress(MemberAddress value) {
      validate(fields()[7], value);
      this.addressBuilder = null;
      this.address = value;
      fieldSetFlags()[7] = true;
      return this;
    }

    /**
      * Checks whether the 'address' field has been set.
      * @return True if the 'address' field has been set, false otherwise.
      */
    public boolean hasAddress() {
      return fieldSetFlags()[7];
    }

    /**
     * Gets the Builder instance for the 'address' field and creates one if it doesn't exist yet.
     * @return This builder.
     */
    public MemberAddress.Builder getAddressBuilder() {
      if (addressBuilder == null) {
        if (hasAddress()) {
          setAddressBuilder(MemberAddress.newBuilder(address));
        } else {
          setAddressBuilder(MemberAddress.newBuilder());
        }
      }
      return addressBuilder;
    }

    /**
     * Sets the Builder instance for the 'address' field
     * @param value The builder instance that must be set.
     * @return This builder.
     */

    public CreateMemberRequestAvroModel.Builder setAddressBuilder(MemberAddress.Builder value) {
      clearAddress();
      addressBuilder = value;
      return this;
    }

    /**
     * Checks whether the 'address' field has an active Builder instance
     * @return True if the 'address' field has an active Builder instance
     */
    public boolean hasAddressBuilder() {
      return addressBuilder != null;
    }

    /**
      * Clears the value of the 'address' field.
      * @return This builder.
      */
    public CreateMemberRequestAvroModel.Builder clearAddress() {
      address = null;
      addressBuilder = null;
      fieldSetFlags()[7] = false;
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
    public CreateMemberRequestAvroModel.Builder setCreatedAt(java.time.Instant value) {
      validate(fields()[8], value);
      this.createdAt = value.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
      fieldSetFlags()[8] = true;
      return this;
    }

    /**
      * Checks whether the 'createdAt' field has been set.
      * @return True if the 'createdAt' field has been set, false otherwise.
      */
    public boolean hasCreatedAt() {
      return fieldSetFlags()[8];
    }


    /**
      * Clears the value of the 'createdAt' field.
      * @return This builder.
      */
    public CreateMemberRequestAvroModel.Builder clearCreatedAt() {
      fieldSetFlags()[8] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public CreateMemberRequestAvroModel build() {
      try {
        CreateMemberRequestAvroModel record = new CreateMemberRequestAvroModel();
        record.memberId = fieldSetFlags()[0] ? this.memberId : (java.lang.String) defaultValue(fields()[0]);
        record.name = fieldSetFlags()[1] ? this.name : (java.lang.String) defaultValue(fields()[1]);
        record.password = fieldSetFlags()[2] ? this.password : (java.lang.String) defaultValue(fields()[2]);
        record.email = fieldSetFlags()[3] ? this.email : (java.lang.String) defaultValue(fields()[3]);
        record.memberKind = fieldSetFlags()[4] ? this.memberKind : (MemberKind) defaultValue(fields()[4]);
        record.memberStatus = fieldSetFlags()[5] ? this.memberStatus : (MemberStatus) defaultValue(fields()[5]);
        record.phoneNumber = fieldSetFlags()[6] ? this.phoneNumber : (java.lang.String) defaultValue(fields()[6]);
        if (addressBuilder != null) {
          try {
            record.address = this.addressBuilder.build();
          } catch (org.apache.avro.AvroMissingFieldException e) {
            e.addParentField(record.getSchema().getField("address"));
            throw e;
          }
        } else {
          record.address = fieldSetFlags()[7] ? this.address : (MemberAddress) defaultValue(fields()[7]);
        }
        record.createdAt = fieldSetFlags()[8] ? this.createdAt : (java.time.Instant) defaultValue(fields()[8]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<CreateMemberRequestAvroModel>
    WRITER$ = (org.apache.avro.io.DatumWriter<CreateMemberRequestAvroModel>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<CreateMemberRequestAvroModel>
    READER$ = (org.apache.avro.io.DatumReader<CreateMemberRequestAvroModel>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}










