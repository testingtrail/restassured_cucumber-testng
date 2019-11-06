Feature: Complex Data Get
  Verify complex data

  @smoke
  Scenario: Verify GET operation for complex data
    Given  I Perform GET operation for path parameter for address "/location/"
      | id    |
      | 1     |
    Then I should see the street name as "1st street" for the "primary" address

