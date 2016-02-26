/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.basics.date;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.strata.basics.market.ReferenceData;
import com.opengamma.strata.basics.market.Resolvable;

/**
 * An adjustment that alters a date if it falls on a day other than a business day.
 * <p>
 * When processing dates in finance, it is typically intended that non-business days,
 * such as weekends and holidays, are converted to a nearby valid business day.
 * This class represents the necessary adjustment.
 * <p>
 * This class combines a {@linkplain BusinessDayConvention business day convention}
 * with a {@linkplain HolidayCalendar holiday calendar}.
 * Together, these contain enough information to be able to adjust a date.
 */
@BeanDefinition
public final class BusinessDayAdjustment
    implements Resolvable<BusinessDayAdjuster>, ImmutableBean, Serializable {

  /**
   * An instance that performs no adjustment.
   */
  public static final BusinessDayAdjustment NONE =
      new BusinessDayAdjustment(BusinessDayConventions.NO_ADJUST, HolidayCalendars.NO_HOLIDAYS);

  /**
   * The convention used to the adjust the date if it does not fall on a business day.
   * <p>
   * The convention determines whether to move forwards or backwards when it is a holiday.
   */
  @PropertyDefinition(validate = "notNull")
  private final BusinessDayConvention convention;
  /**
   * The calendar that defines holidays and business days.
   * <p>
   * When the adjustment is made, this calendar is used to skip holidays.
   */
  @PropertyDefinition(validate = "notNull")
  private final HolidayCalendar calendar;

  //-------------------------------------------------------------------------
  /**
   * Obtains an instance using the specified convention and calendar.
   * <p>
   * When adjusting a date, the convention rule will be applied using the specified calendar.
   * 
   * @param convention  the convention used to the adjust the date if it does not fall on a business day
   * @param calendar  the calendar that defines holidays and business days
   * @return the adjuster
   */
  public static BusinessDayAdjustment of(BusinessDayConvention convention, HolidayCalendar calendar) {
    return new BusinessDayAdjustment(convention, calendar);
  }

  //-------------------------------------------------------------------------
  /**
   * Adjusts the date as necessary if it is not a business day.
   * <p>
   * If the date is a business day it will be returned unaltered.
   * If the date is not a business day, the convention will be applied.
   * 
   * @param date  the date to adjust
   * @param refData  the reference data, used to find the holiday calendar
   * @return the adjusted date
   */
  public LocalDate adjust(LocalDate date, ReferenceData refData) {
    HolidayCalendar holCal = calendar;
    return convention.adjust(date, holCal);
  }

  /**
   * Resolves this adjustment using the specified reference data, returning an adjuster.
   * <p>
   * This returns a {@link BusinessDayAdjuster} that performs the same calculation as this adjustment.
   * It binds the holiday calendar, looked up from the reference data, into the result.
   * As such, there is no need to pass the reference data in again.
   * 
   * @param refData  the reference data, used to find the holiday calendar
   * @return the adjuster, bound to a specific holiday calendar
   */
  @Override
  public BusinessDayAdjuster resolve(ReferenceData refData) {
    return new BusinessDayAdjuster(convention, calendar);
  }

  /**
   * Resolves this adjustment using the specified reference data, returning a date adjuster.
   * <p>
   * This returns a {@link BusinessDayAdjuster} that performs the same calculation as this adjustment.
   * It binds the holiday calendar, looked up from the reference data, into the result.
   * As such, there is no need to pass the reference data in again.
   * <p>
   * See {@link #resolve(ReferenceData)} for an equivalent method that returns a bean.
   * 
   * @param refData  the reference data, used to find the holiday calendar
   * @return the date adjuster, bound to a specific holiday calendar
   */
  public DateAdjuster toDateAdjuster(ReferenceData refData) {
    HolidayCalendar holCal = calendar;
    return date -> convention.adjust(date, holCal);
  }

  //-------------------------------------------------------------------------
  /**
   * Returns a string describing the adjustment.
   * 
   * @return the descriptive string
   */
  @Override
  public String toString() {
    if (this.equals(NONE)) {
      return convention.toString();
    }
    return convention + " using calendar " + calendar.getName();
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code BusinessDayAdjustment}.
   * @return the meta-bean, not null
   */
  public static BusinessDayAdjustment.Meta meta() {
    return BusinessDayAdjustment.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(BusinessDayAdjustment.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static BusinessDayAdjustment.Builder builder() {
    return new BusinessDayAdjustment.Builder();
  }

  private BusinessDayAdjustment(
      BusinessDayConvention convention,
      HolidayCalendar calendar) {
    JodaBeanUtils.notNull(convention, "convention");
    JodaBeanUtils.notNull(calendar, "calendar");
    this.convention = convention;
    this.calendar = calendar;
  }

  @Override
  public BusinessDayAdjustment.Meta metaBean() {
    return BusinessDayAdjustment.Meta.INSTANCE;
  }

  @Override
  public <R> Property<R> property(String propertyName) {
    return metaBean().<R>metaProperty(propertyName).createProperty(this);
  }

  @Override
  public Set<String> propertyNames() {
    return metaBean().metaPropertyMap().keySet();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the convention used to the adjust the date if it does not fall on a business day.
   * <p>
   * The convention determines whether to move forwards or backwards when it is a holiday.
   * @return the value of the property, not null
   */
  public BusinessDayConvention getConvention() {
    return convention;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the calendar that defines holidays and business days.
   * <p>
   * When the adjustment is made, this calendar is used to skip holidays.
   * @return the value of the property, not null
   */
  public HolidayCalendar getCalendar() {
    return calendar;
  }

  //-----------------------------------------------------------------------
  /**
   * Returns a builder that allows this bean to be mutated.
   * @return the mutable builder, not null
   */
  public Builder toBuilder() {
    return new Builder(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      BusinessDayAdjustment other = (BusinessDayAdjustment) obj;
      return JodaBeanUtils.equal(convention, other.convention) &&
          JodaBeanUtils.equal(calendar, other.calendar);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(convention);
    hash = hash * 31 + JodaBeanUtils.hashCode(calendar);
    return hash;
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code BusinessDayAdjustment}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code convention} property.
     */
    private final MetaProperty<BusinessDayConvention> convention = DirectMetaProperty.ofImmutable(
        this, "convention", BusinessDayAdjustment.class, BusinessDayConvention.class);
    /**
     * The meta-property for the {@code calendar} property.
     */
    private final MetaProperty<HolidayCalendar> calendar = DirectMetaProperty.ofImmutable(
        this, "calendar", BusinessDayAdjustment.class, HolidayCalendar.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "convention",
        "calendar");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 2039569265:  // convention
          return convention;
        case -178324674:  // calendar
          return calendar;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BusinessDayAdjustment.Builder builder() {
      return new BusinessDayAdjustment.Builder();
    }

    @Override
    public Class<? extends BusinessDayAdjustment> beanType() {
      return BusinessDayAdjustment.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code convention} property.
     * @return the meta-property, not null
     */
    public MetaProperty<BusinessDayConvention> convention() {
      return convention;
    }

    /**
     * The meta-property for the {@code calendar} property.
     * @return the meta-property, not null
     */
    public MetaProperty<HolidayCalendar> calendar() {
      return calendar;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 2039569265:  // convention
          return ((BusinessDayAdjustment) bean).getConvention();
        case -178324674:  // calendar
          return ((BusinessDayAdjustment) bean).getCalendar();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      metaProperty(propertyName);
      if (quiet) {
        return;
      }
      throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
    }

  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code BusinessDayAdjustment}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<BusinessDayAdjustment> {

    private BusinessDayConvention convention;
    private HolidayCalendar calendar;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(BusinessDayAdjustment beanToCopy) {
      this.convention = beanToCopy.getConvention();
      this.calendar = beanToCopy.getCalendar();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 2039569265:  // convention
          return convention;
        case -178324674:  // calendar
          return calendar;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 2039569265:  // convention
          this.convention = (BusinessDayConvention) newValue;
          break;
        case -178324674:  // calendar
          this.calendar = (HolidayCalendar) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public Builder set(MetaProperty<?> property, Object value) {
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setString(String propertyName, String value) {
      setString(meta().metaProperty(propertyName), value);
      return this;
    }

    @Override
    public Builder setString(MetaProperty<?> property, String value) {
      super.setString(property, value);
      return this;
    }

    @Override
    public Builder setAll(Map<String, ? extends Object> propertyValueMap) {
      super.setAll(propertyValueMap);
      return this;
    }

    @Override
    public BusinessDayAdjustment build() {
      return new BusinessDayAdjustment(
          convention,
          calendar);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the convention used to the adjust the date if it does not fall on a business day.
     * <p>
     * The convention determines whether to move forwards or backwards when it is a holiday.
     * @param convention  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder convention(BusinessDayConvention convention) {
      JodaBeanUtils.notNull(convention, "convention");
      this.convention = convention;
      return this;
    }

    /**
     * Sets the calendar that defines holidays and business days.
     * <p>
     * When the adjustment is made, this calendar is used to skip holidays.
     * @param calendar  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder calendar(HolidayCalendar calendar) {
      JodaBeanUtils.notNull(calendar, "calendar");
      this.calendar = calendar;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(96);
      buf.append("BusinessDayAdjustment.Builder{");
      buf.append("convention").append('=').append(JodaBeanUtils.toString(convention)).append(',').append(' ');
      buf.append("calendar").append('=').append(JodaBeanUtils.toString(calendar));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
