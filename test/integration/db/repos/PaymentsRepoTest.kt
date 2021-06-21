package integration.db.repos

import db.repos.PaymentsRepo
import kodein
import kotlinx.coroutines.runBlocking
import models.Payment
import org.bson.types.ObjectId
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.kodein.di.generic.instance
import org.litote.kmongo.id.toId
import utils.sealed.EntityType
import utils.sealed.PaymentType
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class PaymentsRepoTest {

    private val repo: PaymentsRepo by kodein.instance()
    private val payment =
        Payment(ObjectId("60a81be79827071039aac049").toId(), EntityType.Student, PaymentType.Incoming, 5000F)

    @Order(1)
    @Test
    fun test_add_payment() {
        runBlocking {
            val result = repo.addPayment(payment)
            assertTrue(result.wasAcknowledged())
        }
    }

    @Order(2)
    @Test
    fun test_update_payment() {
        runBlocking {
            val result = repo.updatePayment(payment)
            println(result.modifiedCount)
            assertTrue(result.matchedCount == 1L)
        }
    }

    @Order(3)
    @Test
    fun test_get_all_payments() {
        runBlocking {
            val paymentsList = repo.getAllPayments()
            assertTrue(paymentsList.isNotEmpty())
            assertTrue(paymentsList.contains(payment))
        }
    }

    @Order(4)
    @Test
    fun test_search_for_existing_payments_1() {
        runBlocking {
            val paymentsList = repo.searchForPayments(Payment(paymentType = PaymentType.Incoming))
            assertTrue(paymentsList.isNotEmpty())
            assertTrue(paymentsList.contains(payment))
        }
    }

    @Order(5)
    @Test
    fun test_search_for_existing_payments_2() {
        runBlocking {
            val paymentsList = repo.searchForPayments(Payment(amount = 5000F, entityType = EntityType.Student))
            assertTrue(paymentsList.isNotEmpty())
            assertTrue(paymentsList.contains(payment))
        }
    }

    @Order(6)
    @Test
    fun test_search_for_non_existing_payments() {
        runBlocking {
            val paymentsList = repo.searchForPayments(Payment(amount = 4000F))
            assertTrue(paymentsList.isEmpty())
            assertFalse(paymentsList.contains(payment))
        }
    }

    @Order(7)
    @Test
    fun test_delete_payment() {
        runBlocking {
            val result = repo.deletePayment(payment)
            assertTrue(result != null)
        }
    }


}