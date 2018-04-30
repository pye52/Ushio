package com.kanade.ushio.entity

import com.chad.library.adapter.base.entity.SectionEntity

class CalendarSection : SectionEntity<Subject> {
    constructor(isHeader: Boolean, header: String) : super(isHeader, header)

    constructor(subject: Subject) : super(subject)
}