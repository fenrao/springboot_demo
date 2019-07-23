package com.example.print_demo.controller

import com.itextpdf.text.Document
import com.itextpdf.text.pdf.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

import org.springframework.web.servlet.ModelAndView

import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
@RequestMapping("report")
class Printcontroller {
    @Autowired
    val env: Environment ?= null

    @GetMapping("/index")
    fun report(): ModelAndView {
        return ModelAndView("index")

    }

    @PostMapping("/print")
    fun print(response: HttpServletResponse,request: HttpServletRequest) {
        var templatePath= env?.getProperty("receipt.model")+"pdfTemplate.pdf"
        val fontFile = env?.getProperty("font_size")+"simkai.ttf";
        val ChineseBaseFont = BaseFont.createFont(fontFile, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED)
        var baos: ByteArrayOutputStream = ByteArrayOutputStream()
        var reader = PdfReader(templatePath)
        var stramper: PdfStamper = PdfStamper(reader, baos)
        var form: AcroFields = stramper.getAcroFields()
        form.addSubstitutionFont(ChineseBaseFont)    //设置字体

        val dateFormt = SimpleDateFormat("yyyy-MM-dd")
        var date = dateFormt.format(Date())   //打印时间
        form.setField("name","xxxx")
        form.setField("password","123456")
        stramper.setFormFlattening(true)
        stramper.close()
        var pd = PdfReader(baos.toByteArray())
        var document = Document()
        var bao = ByteArrayOutputStream()
        var pdfCopy = PdfCopy(document, bao)
        document.open()
        var impPage = pdfCopy.getImportedPage(pd, 1)
        pdfCopy.addPage(impPage)
        reader.close()
        document.close()
        var out: OutputStream = response.getOutputStream();
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition")
        response.setHeader("Access-Control-Allow-Origin", "*")
        response.setContentType("application/pdf");
        response.setHeader("Content-Type", "application/pdf");
        response.setHeader("Content-disposition", "inline;filename=" + UUID.randomUUID().toString() + ".pdf")//attachment：下载pdf  inline：预览pdf
        response.setContentLength(bao.size())
        response.setHeader("Cache-Control", "max-age=30")
        response.setCharacterEncoding("UTF-8")

        bao.writeTo(out)
        out.flush()
        out.close()
    }


}