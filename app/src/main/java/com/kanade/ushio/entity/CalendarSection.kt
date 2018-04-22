package com.kanade.ushio.entity

import com.chad.library.adapter.base.entity.SectionEntity

class CalendarSection : SectionEntity<CalendarSubject> {
    constructor(isHeader: Boolean, header: String) : super(isHeader, header)

    constructor(subject: CalendarSubject) : super(subject)
}