package davidbrownasttransformation.app

import grails.gorm.services.Service
import grails.gorm.transactions.ReadOnly

@Service(Customer)
abstract class CustomerService {
    abstract Customer save(String name, Date dateDeleted)

    @ReadOnly
    void logDeleted() {
        List notDeleted = Customer.listNotDeleted()
        println "There are ${notDeleted.size()} not-deleted records"
    }
}
