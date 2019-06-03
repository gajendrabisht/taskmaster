Feature: Update a task

  Scenario: Task not found
    When I update task '11111111-1111-1111-1111-111111111111'
      | title | description |
      | test  | test        |
    Then I should get response status 404
    And I should get error message '{"errors":["Task: 11111111-1111-1111-1111-111111111111 Not Found"]}'

  Scenario: Update a task
    Given tasks exist
      | id                                   | title        | description        |
      | 11111111-1111-1111-1111-111111111111 | Iron Clothes | Get ready for work |
    When I update task '11111111-1111-1111-1111-111111111111'
      | title     | description     |
      | New Title | New Description |
    Then I should get response status 200
    And task exists in database
      | id                                   | title     | description     |
      | 11111111-1111-1111-1111-111111111111 | New Title | New Description |         