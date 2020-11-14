INSERT INTO bank (`id`, `name`) VALUES
  (1, 'SCB'),
  (2, 'HSBC');
  
INSERT INTO branch (`id`, `bank_id`, `name`) VALUES
  (1, 1, 'SCB-Banani-Branch'),
  (2, 1, 'SCB-Gulshan-Branch'),
  (3, 1, 'SCB-Motijheel-Branch'),
  (4, 2, 'HSBC-Banani-Branch'),
  (5, 2, 'HSBC-Gulshan-Branch');
  
INSERT INTO `account_type` (`id`, `name`) VALUES
(1, 'Savings'),
(2, 'Current')
;

INSERT INTO `account` (`id`, `account_name`, `current_balance`, `account_type_id`, `bank_id`, `branch_id`) VALUES 
('1', 'XYZ Company', '100000', '1', '1', '1'),
('2', 'Mr. John Doe', '1000', '1', '1', '1')
;

INSERT INTO `salarymanager`.`lower_basic` (`id`, `value`) VALUES ('1', '25000');


INSERT INTO `grade` (`id`, `name`, `basic_salary`) VALUES
(1, 'Grade-1',50000),
(2, 'Grade-2',45000),
(3, 'Grade-3',40000),
(4, 'Grade-4',35000),
(5, 'Grade-5',30000),
(6, 'Grade-6',25000)
;

INSERT INTO `company` (`id`, `name`, `account_id`) VALUES 
('1', 'XYZ Company', '1');

INSERT INTO `employee` (`id`, `address`, `mobile`, `name`, `account_id`, `grade_id`) VALUES
('1', 'Dhaka', '+8801101234567', 'Mr. John Doe', '2', '6');

