package davidbrownasttransformation.app

import org.grails.orm.hibernate.HibernateDatastore

class App {
    static void main(String[] args) {
        Map configuration = [
                'hibernate.hbm2ddl.auto': 'create-drop',
                'dataSource.url'        : 'jdbc:h2:mem:myDB'
        ]
        def datastore = new HibernateDatastore(configuration, Customer)
        def customerService = datastore.getService(CustomerService)

        customerService.logDeleted()

        customerService.save 'First', null
        customerService.save 'Second', null
        customerService.save 'Third', new Date()
        customerService.save 'Fourth', new Date()
        customerService.save 'Fifth', new Date()

        customerService.logDeleted()
    }
}
