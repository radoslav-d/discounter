#Discounter

Exercise project writen in Spring Boot, which exposes an API such that given a bill, it finds the net discount.

Requirements:
1. If the user is an employee of the store, he gets a 30% discount
2. If the user is an affiliate of the store, he gets a 10% discount
3. If the user has been a customer for over 2 years, he gets a 5% discount.
4. For every $100 on the bill, there would be a $ 5 discount (e.g. for $ 990, you get $ 45 as a discount).
5. The percentage based discounts do not apply on groceries.
6. A user can get only one of the percentage based discounts on a bill.

The solution exposes one rest end point - post method, which accepts the following structure for payment information:
{
	"userInfo": {
		"type": enumeration,
		"data": object
	},
	"amount": number,
	"otherData": object,
	"containsGroceries": boolean
}

- The "amount" property is the total bill.

- The "userInfo" property can be extended to keep more data, but its key data is the "type" property, which can be the following enumerations ["EMPLOYEE", "AFFILIATE", "LOYAL", "REGULAR"]. If this property is not specified it is assigned as "REGULAR".
    The "EMPLOYEE" enum describes a user that is an employee of the store.
    The "AFFILIATE" enum describes a user that is affiliate of the store.
    The "LOYAL" enum describes a user that has been a customer for over 2 years.
    The "REGULAR" enum describes a user type, to which the percentage discount does not applies.

- The "containsGroceries" tells whether the purchased stocks include groceries.

The returned response has the following structure:
{
    "discount": number,
    "totalBill": number,
    "type": enumeration
}

The "discount" property is the total amount of the discount, the "totalBill" is the initial bill and the type corresponds to the discount type applied.
There are 4 options:

    EMPLOYEE_DISCOUNT - discount for employees;

    AFFILIATE_DISCOUNT - discount for affiliate user;

    LOYALTY_DISCOUNT - discount for user that has been customer for more that 2 years;

    NONE - no discount is applied.

To every payment an additional "voucher based" is applied, which corresponds to point 4. of the requirements.

Example request and response:
    - Request:
    {
        "userInfo": {
            "type": "AFFILIATE",
            "data": "affiliate user example"
        },
        "amount": 323.44,
        "otherData": "request example",
        "containsGroceries": false
    }
    - Response:
    {
        "discount": 47.344,
        "totalBill": 323.44,
        "type": "AFFILIATE_DISCOUNT"
    }



Run "mvn clean install" to recompile sources.
Run "mvn test" to run tests and generate code coverage report (placed in: /target/site/jacoco/index.html, open in browser to view).
Run "DiscounterApplication" class to start the server.

This project contains "class-diagram.jpg", which is an image, displaying the UML diagrams of the Java classes.

Solution approach:

1. There are several data classes, describing the existing models in the project, mainly the discount and the payment.
2. Service based approach, where service classes are injected into other services.
3. There is a dedicated service, which calculates the discounts based on the payment information.
4. There is a service, which serves as a map between the user type and the logic, which should be applied to calculate the discount. The idea is to escape the numerous if-else or switch statements and get a complexity of 1. To every type of user, a discount type is mapped. Additionally, to every discount type, a "callback" function is mapped, which is the custom logic for calculating the discount.

Test approach:

There are several test classes, which simply assert that the services work correctly. There is one "integration" test for the rest service, which mocks http request and asserts that responses. Some of the test classes share the same test logic, so a dedicated parent class is defined for them.
There are no test cases for the data classes, because they are simply getters and setters.


