package com.goldcompany.apps.goodthinking.data.db.converter

import androidx.room.TypeConverter
import com.goldcompany.apps.goodthinking.feature.chat.Participant

class ParticipantConverter {
    @TypeConverter
    fun toParticipant(value: Int): Participant = enumValues<Participant>()[value]

    @TypeConverter
    fun fromParticipant(value: Participant): Int = value.ordinal
}