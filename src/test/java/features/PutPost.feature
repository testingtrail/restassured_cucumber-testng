Feature: PutPOST
  Test the put operation

  @smoke
  Scenario: Veriry PUT operation after POST
    Given I ensure to Perform POST operation for "/posts" with body as
      | id  | title              | author                 |
      | 6   | API Testing course | ExecuteAutomation      |
    And I Perform PUT operation for "/posts/{postid}/"
      | id  | title              | author                 |
      | 6   | API Testing        | ExecuteAutomation      |
    And I perform GET operation with path parameter for "/posts/{postid}/"
      | postid  |
      | 6       |
    Then I "should" see the body with title "API Testing"