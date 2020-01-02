Feature: Integration test

  Scenario: Call an endpoint
    Given there are 3 people in the database
    When the client makes a GET request to "/personen"
    Then the HTTP status code should be 200 and the result should contain 3 elements
