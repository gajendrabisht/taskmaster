Feature: Delete a task

  Scenario: Task not found
    When I delete task '11111111-1111-1111-1111-111111111111'
    Then I should get response status 404
    And I should get error message '{"errors":["Task: 11111111-1111-1111-1111-111111111111 Not Found"]}'

  Scenario: Delete a task
    Given tasks exist
      | id                                   | title        | description        |
      | 11111111-1111-1111-1111-111111111111 | Iron Clothes | Get ready for work |
    When I delete task '11111111-1111-1111-1111-111111111111'
    Then I should get response status 200
    And no tasks exist in database
