package com.pixelprodukt.healthapprestbackend.model.bloodpressure

import com.pixelprodukt.healthapprestbackend.model.audit.DateAudit
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "blood_pressures")
class BloodPressure(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,
    //@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id") var user: User,
    @Column(name = "user_id") var userId: Long,
    @NotNull var systolicValue: Short,
    @NotNull var diastolicValue: Short,
    @NotNull var pulseRate: Short,
    @NotNull var measuredAt: Date,
    var armSide: String,
    @Size(max = 600) var comment: String
): DateAudit()