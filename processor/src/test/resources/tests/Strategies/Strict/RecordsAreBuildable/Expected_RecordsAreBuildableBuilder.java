package io.jonasg.bob.test;

import io.jonasg.bob.MandatoryFieldMissingException;
import io.jonasg.bob.MandatoryFieldsMissingException;
import io.jonasg.bob.MissingField;
import io.jonasg.bob.ValidatableField;
import java.lang.Boolean;
import java.lang.Double;
import java.lang.Float;
import java.lang.Integer;
import java.lang.String;

public final class RecordsAreBuildableBuilder {
  private final ValidatableField<String> make = ValidatableField.ofNoneNullableField("make", "RecordsAreBuildable");

  private final ValidatableField<Integer> year = ValidatableField.ofNoneNullableField("year", "RecordsAreBuildable");

  private final ValidatableField<Double> engineSize = ValidatableField.ofNoneNullableField("engineSize", "RecordsAreBuildable");

  private final ValidatableField<Boolean> isElectric = ValidatableField.ofNoneNullableField("isElectric", "RecordsAreBuildable");

  private final ValidatableField<Float> fuelEfficiency = ValidatableField.ofNoneNullableField("fuelEfficiency", "RecordsAreBuildable");

  public RecordsAreBuildableBuilder() {
  }

  public RecordsAreBuildableBuilder make(String make) {
    this.make.set(make);
    return this;
  }

  public RecordsAreBuildableBuilder year(int year) {
    this.year.set(year);
    return this;
  }

  public RecordsAreBuildableBuilder engineSize(double engineSize) {
    this.engineSize.set(engineSize);
    return this;
  }

  public RecordsAreBuildableBuilder isElectric(boolean isElectric) {
    this.isElectric.set(isElectric);
    return this;
  }

  public RecordsAreBuildableBuilder fuelEfficiency(float fuelEfficiency) {
    this.fuelEfficiency.set(fuelEfficiency);
    return this;
  }

  public RecordsAreBuildable build() {
    var missingFields = new java.util.ArrayList<String>();
    if (!make.isValid()) missingFields.add("make");
    if (!year.isValid()) missingFields.add("year");
    if (!engineSize.isValid()) missingFields.add("engineSize");
    if (!isElectric.isValid()) missingFields.add("isElectric");
    if (!fuelEfficiency.isValid()) missingFields.add("fuelEfficiency");
    if (missingFields.size() == 1) {
      throw new MandatoryFieldMissingException(missingFields.get(0), "RecordsAreBuildable");
    } else if (!missingFields.isEmpty()) {
      throw new MandatoryFieldsMissingException(missingFields.stream().map(f -> new MissingField(f, "RecordsAreBuildable")).toList());
    }
    return new RecordsAreBuildable(make.get(), year.get(), engineSize.get(), isElectric.get(), fuelEfficiency.get());
  }
}
