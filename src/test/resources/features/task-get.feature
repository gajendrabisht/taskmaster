Feature: Get Task by ID

  Scenario: Task not found
    When I request task id '11111111-1111-1111-1111-111111111111'
    Then I should get response status 404
    And I should get error message '{"errors":["Task: 11111111-1111-1111-1111-111111111111 Not Found"]}'

  Scenario: Get task
    Given tasks exist
      | id                                   | title        | description        |
      | 11111111-1111-1111-1111-111111111111 | Iron Clothes | Get ready for work |
      | 22222222-2222-2222-2222-222222222222 | Wax Shoes    | Look good          |
    When I request task id '11111111-1111-1111-1111-111111111111'
    Then I should get response status 200
    And I should get task returned as
      | id                                   | title        | description        |
      | 11111111-1111-1111-1111-111111111111 | Iron Clothes | Get ready for work |

  Scenario: Get all tasks
    Given tasks exist
      | id                                   | title        | description        |
      | 11111111-1111-1111-1111-111111111111 | Iron Clothes | Get ready for work |
      | 22222222-2222-2222-2222-222222222222 | Wax Shoes    | Look good          |
    When I request all tasks
    Then I should get response status 200
    And I should get tasks returned as
      | id                                   | title        | description        |
      | 11111111-1111-1111-1111-111111111111 | Iron Clothes | Get ready for work |
      | 22222222-2222-2222-2222-222222222222 | Wax Shoes    | Look good          |
