package com.example.scanner

import android.content.Context
import android.util.Log
import com.example.scanner.Model.ModelToPDF.*
import io.realm.Realm

class RealmManager() {
    fun settextObj( realm: Realm, text : Text) {
        val temp = text.toRealmObject()
        realm.executeTransaction{
            realm.copyToRealm(temp)
        }
    }
    fun gettextObj(realm: Realm): ArrayList<Text> {
        val arrTextObj : List<TextObject> = realm.where(TextObject::class.java).findAll()
        val arrText = ArrayList<Text>()
        var text = Text()
        for (i in arrTextObj.indices){
            text = arrTextObj[i].getObject()
            arrText.add(text)
        }
        return arrText
    }

    fun setPageHeaderObj(realm: Realm, pageHeader: PageHeader) {
        val temp = pageHeader.toRealmObject()
        realm.executeTransaction{
            realm.copyToRealm(temp)
        }
    }
    fun getPageHeaderObj(realm: Realm): ArrayList<PageHeader> {
        val arrPageheaderObj : List<HeaderObject> = realm.where(HeaderObject::class.java).findAll()
        val arrPageHeader = ArrayList<PageHeader>()
        var PageHeader = PageHeader()
        for (i in arrPageheaderObj.indices){
            PageHeader = arrPageheaderObj[i].getObject()
            PageHeader.text = gettextObj(realm)[i]
            arrPageHeader.add(PageHeader)
        }
        return arrPageHeader
    }

    fun setCellObj(realm: Realm, cell: Cell) {
        val temp = cell.toRealmObject()
        realm.executeTransaction{
            realm.copyToRealm(temp)
        }
    }
    fun getCellObj(realm: Realm): ArrayList<Cell> {
        val arrCellObj : List<CellObject> = realm.where(CellObject::class.java).findAll()
        val arrCell = ArrayList<Cell>()
        var Cell = Cell()
        for (i in arrCellObj.indices){
            Cell = arrCellObj[i].getObject()
            arrCell.add(Cell)
        }
        return arrCell
    }
    fun deleteCellAll(realm: Realm){
        val data = realm!!.where(CellObject::class.java).findAll()
        realm!!.executeTransaction {
            data?.deleteAllFromRealm()
        }
        Log.d("Delete status","Data deleted finish!!!")
    }
    fun deleteTextAll(realm: Realm){
        val data = realm!!.where(TextObject::class.java).findAll()
        realm!!.executeTransaction {
            data?.deleteAllFromRealm()
        }
        Log.d("Delete status","Data deleted finish!!!")
    }
    fun deleteHeaderAll(realm: Realm){
        val data = realm!!.where(HeaderObject::class.java).findAll()
        realm!!.executeTransaction {
            data?.deleteAllFromRealm()
        }
        Log.d("Delete status","Data deleted finish!!!")
    }
}