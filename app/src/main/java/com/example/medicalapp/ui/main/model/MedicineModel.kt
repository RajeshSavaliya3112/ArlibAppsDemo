package com.example.medicalapp.ui.main.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MedicineModel(

	@SerializedName("problems")
	val problems: List<ProblemsItem?>? = null
)

data class AssociatedDrug2Item(

	@SerializedName("dose")
	val dose: String? = null,

	@SerializedName("strength")
	val strength: String? = null,

	@SerializedName("name")
	val name: String? = null
)

data class ClassName2Item(

	@SerializedName("associatedDrug")
	val associatedDrug: List<AssociatedDrugItem?>? = null,

	@SerializedName("associatedDrug#2")
	val associatedDrug2: List<AssociatedDrug2Item?>? = null
)

data class ClassNameItem(

	@SerializedName("associatedDrug")
	val associatedDrug: List<AssociatedDrugItem?>? = null,

	@SerializedName("associatedDrug#2")
	val associatedDrug2: List<AssociatedDrug2Item?>? = null
)

data class LabsItem(

	@SerializedName("missing_field")
	val missingField: String? = null
)

data class AssociatedDrugItem(

	@SerializedName("dose")
	val dose: String? = null,

	@SerializedName("strength")
	val strength: String? = null,

	@SerializedName("name")
	val name: String? = null
) : Serializable

data class ProblemsItem(

	@SerializedName("Diabetes")
	val diabetes: List<DiabetesItem?>? = null,

)

data class MedicationsItem(

	@SerializedName("medicationsClasses")
	val medicationsClasses: List<MedicationsClassesItem?>? = null
)

data class DiabetesItem(

	@SerializedName("labs")
	val labs: List<LabsItem?>? = null,

	@SerializedName("medications")
	val medications: List<MedicationsItem?>? = null
)

data class MedicationsClassesItem(

	@SerializedName("className2")
	val className2: List<ClassName2Item?>? = null,

	@SerializedName("className")
	val className: List<ClassNameItem?>? = null
)
