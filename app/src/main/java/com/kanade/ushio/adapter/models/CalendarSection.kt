package com.kanade.ushio.adapter.models

import com.chad.library.adapter.base.entity.SectionEntity
import com.kanade.ushio.entity.subject.SubjectSimple

class CalendarSection : SectionEntity<SubjectSimple> {
    constructor(isHeader: Boolean, header: String) : super(isHeader, header)

    constructor(subject: SubjectSimple) : super(subject)
}