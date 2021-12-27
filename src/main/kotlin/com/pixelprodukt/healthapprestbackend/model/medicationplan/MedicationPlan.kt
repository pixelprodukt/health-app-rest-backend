package com.pixelprodukt.healthapprestbackend.model.medicationplan

import com.pixelprodukt.healthapprestbackend.model.audit.DateAudit
import javax.persistence.*

@Entity
@Table(name = "medication_plans")
class MedicationPlan(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,
    @Column(name = "user_id") var userId: Long,
): DateAudit()