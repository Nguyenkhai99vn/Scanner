package com.example.scanner.Model.ModelToPDF

import com.example.scanner.Model.ModelToPDF.Enum.*
import kotlinx.coroutines.selects.select


class Item {
    var fileID: String =""
    // Used to save file PDF, Word
    var fileType: FileType? = null // PDF, WORD, Item . File exist // created
    var fileUrl: String? = null // URL to file

    var title: String = ""
    var dateCreated: String = ""
    var dateModified: String = ""
    // select mode
    var isSelect: Boolean = false  // ko can luu database

    var subItems = ArrayList<DetailItem>() // tuong dang 1 mang image // 1 arr image // thay no = 1 mang image
    // add new
//    var printType : PrintType = PrintType(type: .all, pageFrom: 1, pageTo: 1) // Khoan da~ bo vao
//    var passWord : FilePassWord!

    var imageSolution: ImageResolution = ImageResolution.normal
    var viewMode = 0 // view mode = 0 -> go to Image, Viewmode = 1 : Go to Customize screen // tam thoi ko can
    var fileTitle: Text? = null// Used to draw in the title of PDF file
    var subFileTitle: Text? = null // Used to draw bellow title
//    var pageSize : PageSize!
    var margin: Margin = Margin.Normal
    var orientation: Orientation = Orientation.portrait // tam thoi chua lam
    var pageHeader: PageHeader = PageHeader()
    var pageFooter: PageHeader = PageHeader()
    var coverPage: Layout? = null
    var texts = ArrayList<Text>()
//    var defaultLayout: Layout = Layout()
    var imageScale: ImageScale = ImageScale.fit
    var currImageSelectedIdx = 0

//    func sortSubItemWithPageIdx() {
//        //CycTrung found issue here
//        let sortedList = self.subItems.sorted(by: { $0.pageNumber < $1.pageNumber })
//        self.subItems = sortedList
//    }
//    func reOrderPageNum() {
//        var idx = 1
//        for itDetail in (self.subItems)! {
//            itDetail.pageNumber = idx
//            idx = idx + 1
//        }
//
//    }

}