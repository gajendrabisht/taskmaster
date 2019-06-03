Feature: Add a task

  Scenario: Add a task
    When I add a task
      | title        | description        |
      | Iron Clothes | Get ready for work |
    Then I should get response status 201
    And I should get newly created pass as
      | title        | description        |
      | Iron Clothes | Get ready for work |
    And task exists in database
      | title        | description        |
      | Iron Clothes | Get ready for work |