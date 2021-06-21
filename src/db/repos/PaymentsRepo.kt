package db.repos

import com.mongodb.client.model.Filters
import db.constants.DBConstants
import kodein
import models.Payment
import org.bson.types.ObjectId
import org.kodein.di.generic.instance
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.updateOne
import org.litote.kmongo.eq
import org.litote.kmongo.id.toId

class PaymentsRepo {

    private val db: CoroutineDatabase by kodein.instance()
    private val col = db.getCollection<Payment>(DBConstants.PAYMENTS_COL_NAME)

    suspend fun getAllPayments() = col.find().toList()

    suspend fun getLastPayments() = col.find().limit(100).descendingSort(Payment::date).toList()

    suspend fun getPayment(id: String) = col.findOneById(ObjectId(id))

    suspend fun searchForPayments(payment: Payment): List<Payment> {
        return col.find(
            payment.paymentType?.let {
                Filters.regex(
                    Payment::paymentType.name,
                    it.value,
                    "i"
                )
            }, payment.entityType?.let {
                Filters.regex(
                    Payment::entityType.name,
                    it.value,
                    "i"
                )
            }, payment.amount?.let {
                Filters.eq(
                    Payment::amount.name,
                    it,
                )
            }
        ).toList()
    }

    suspend fun addPayment(payment: Payment) = col.insertOne(payment)

    suspend fun updatePayment(payment: Payment) = col.updateOne(payment)

    suspend fun deletePayment(payment: Payment) = deletePayment(payment._id.toString())

    suspend fun deletePayment(id: String) = col.findOneAndDelete(Payment::_id eq ObjectId(id).toId())

}