package davidbrownasttransformation.app

import davidbrownasttransformation.utilities.MyAnnotation
import grails.gorm.annotation.Entity

@Entity
@MyAnnotation
class Customer {
    Date dateDeleted
    String name
    static constraints = {
        dateDeleted nullable: true
    }
}
