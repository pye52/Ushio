package com.kanade.ushio.ui.about.xml_parser

import org.xml.sax.helpers.DefaultHandler

class UpdateParserHandler : DefaultHandler() {
    private lateinit var result: UpdateResponse
    private var ver = 0
    private var text = ""
    private var content = ""

    fun getResult(): UpdateResponse = result

    override fun characters(ch: CharArray, start: Int, length: Int) {
        super.characters(ch, start, length)
        content = String(ch, start, length)
    }

    override fun endElement(uri: String, localName: String, qName: String) {
        super.endElement(uri, localName, qName)
        when (localName) {
            "ver" -> ver = content.toInt()
            "text" -> text = content
        }
    }

    override fun endDocument() {
        super.endDocument()
        result = UpdateResponse(ver, text)
    }
}