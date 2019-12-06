Feature:
  Verify different GET operations using RESTassured

  Scenario: Verify author of the post
    Given I perform GET operation for "/posts/1"
    Then I should see the author name as "Karthik KK"

  Scenario: Verify collection of authors in the post
    Given I perform GET operation for "/posts"
    Then I should see the author names

  Scenario: Verify Parameter of Get
    Given I perform GET operation for "/posts"
    Then I should verify GET Parameter

  Scenario: Verify GET operator with Json validation
    Given I perform GET operation for "/posts/1"
    Then I should see the author name as "Karthik KK" with JSON validation


