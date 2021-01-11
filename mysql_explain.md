# mysql explain 用法说明

本节描述EXPLAIN生成的输出列。后面的部分将提供关于type和extra列的额外信息。

 Each output row from [`EXPLAIN`](https://dev.mysql.com/doc/refman/8.0/en/explain.html) provides information about one table. Each row contains the values summarized in [Table 8.1, “EXPLAIN Output Columns”](https://dev.mysql.com/doc/refman/8.0/en/explain-output.html#explain-output-column-table), and described in more detail following the table. Column names are shown in the table's first column; the second column provides the equivalent property name shown in the output when `FORMAT=JSON` is used.

**Table 8.1 EXPLAIN Output Columns**

| Column                                                       | JSON Name       | Meaning                                        |
| :----------------------------------------------------------- | :-------------- | :--------------------------------------------- |
| [`id`](https://dev.mysql.com/doc/refman/8.0/en/explain-output.html#explain_id) | `select_id`     | The `SELECT` identifier                        |
| [`select_type`](https://dev.mysql.com/doc/refman/8.0/en/explain-output.html#explain_select_type) | None            | The `SELECT` type                              |
| [`table`](https://dev.mysql.com/doc/refman/8.0/en/explain-output.html#explain_table) | `table_name`    | The table for the output row                   |
| [`partitions`](https://dev.mysql.com/doc/refman/8.0/en/explain-output.html#explain_partitions) | `partitions`    | The matching partitions                        |
| [`type`](https://dev.mysql.com/doc/refman/8.0/en/explain-output.html#explain_type) | `access_type`   | The join type                                  |
| [`possible_keys`](https://dev.mysql.com/doc/refman/8.0/en/explain-output.html#explain_possible_keys) | `possible_keys` | The possible indexes to choose                 |
| [`key`](https://dev.mysql.com/doc/refman/8.0/en/explain-output.html#explain_key) | `key`           | The index actually chosen                      |
| [`key_len`](https://dev.mysql.com/doc/refman/8.0/en/explain-output.html#explain_key_len) | `key_length`    | The length of the chosen key                   |
| [`ref`](https://dev.mysql.com/doc/refman/8.0/en/explain-output.html#explain_ref) | `ref`           | The columns compared to the index              |
| [`rows`](https://dev.mysql.com/doc/refman/8.0/en/explain-output.html#explain_rows) | `rows`          | Estimate of rows to be examined                |
| [`filtered`](https://dev.mysql.com/doc/refman/8.0/en/explain-output.html#explain_filtered) | `filtered`      | Percentage of rows filtered by table condition |
| [`Extra`](https://dev.mysql.com/doc/refman/8.0/en/explain-output.html#explain_extra) | None            | Additional information                         |

一，`id` (JSON name: `select_id`)

The [`SELECT`](https://dev.mysql.com/doc/refman/8.0/en/select.html) identifier. This is the sequential number of the [`SELECT`](https://dev.mysql.com/doc/refman/8.0/en/select.html) within the query. The value can be `NULL` if the row refers to the union result of other rows. In this case, the `table` column shows a value like `<union*`M`*,*`N`*>` to indicate that the row refers to the union of the rows with `id` values of *`M`* and *`N`*.

|                     `select_type` Value                      | JSON Name                    | Meaning                                                      |
| :----------------------------------------------------------: | :--------------------------- | :----------------------------------------------------------- |
|                           `SIMPLE`                           | None                         | Simple [`SELECT`](https://dev.mysql.com/doc/refman/8.0/en/select.html) (not using [`UNION`](https://dev.mysql.com/doc/refman/8.0/en/union.html) or subqueries) |
|                          `PRIMARY`                           | None                         | Outermost [`SELECT`](https://dev.mysql.com/doc/refman/8.0/en/select.html) |
| [`UNION`](https://dev.mysql.com/doc/refman/8.0/en/union.html) | None                         | Second or later [`SELECT`](https://dev.mysql.com/doc/refman/8.0/en/select.html) statement in a [`UNION`](https://dev.mysql.com/doc/refman/8.0/en/union.html) |
|                      `DEPENDENT UNION`                       | `dependent` (`true`)         | Second or later [`SELECT`](https://dev.mysql.com/doc/refman/8.0/en/select.html) statement in a [`UNION`](https://dev.mysql.com/doc/refman/8.0/en/union.html), dependent on outer query |
|                        `UNION RESULT`                        | `union_result`               | Result of a [`UNION`](https://dev.mysql.com/doc/refman/8.0/en/union.html). |
| [`SUBQUERY`](https://dev.mysql.com/doc/refman/8.0/en/optimizer-hints.html#optimizer-hints-subquery) | None                         | First [`SELECT`](https://dev.mysql.com/doc/refman/8.0/en/select.html) in subquery |
|                     `DEPENDENT SUBQUERY`                     | `dependent` (`true`)         | First [`SELECT`](https://dev.mysql.com/doc/refman/8.0/en/select.html) in subquery, dependent on outer query |
|                          `DERIVED`                           | None                         | Derived table                                                |
|                     `DEPENDENT DERIVED`                      | `dependent` (`true`)         | Derived table dependent on another table                     |
|                        `MATERIALIZED`                        | `materialized_from_subquery` | Materialized subquery                                        |
|                    `UNCACHEABLE SUBQUERY`                    | `cacheable` (`false`)        | A subquery for which the result cannot be cached and must be re-evaluated for each row of the outer query |
|                     `UNCACHEABLE UNION`                      | `cacheable` (`false`)        | The second or later select in a [`UNION`](https://dev.mysql.com/doc/refman/8.0/en/union.html) that belongs to an uncacheable subquery (see `UNCACHEABLE SUBQUERY`) |

`DEPENDENT `通常表示使用相关的子查询。参见13.2.11.7节“关联子查询”。

`DEPENDENT SUBQUERY` evaluation differs from `UNCACHEABLE SUBQUERY` evaluation,For `DEPENDENT SUBQUERY`, the subquery is re-evaluated only once for each set of different values of the variables from its outer context. For `UNCACHEABLE SUBQUERY`, the subquery is re-evaluated for each row of the outer context.

`DEPENDENT SUBQUERY`依赖子查询计算不同于不可缓存子查询计算。对于依赖子查询，对于外部上下文中的变量的每一组不同值，子查询只重新计算一次。对于不可缓存的子查询，将为外部上下文的每一行重新计算子查询。

When you specify `FORMAT=JSON` with `EXPLAIN`, the output has no single property directly equivalent to `select_type`; the `query_block` property corresponds to a given `SELECT`. Properties equivalent to most of the `SELECT` subquery types just shown are available (an example being `materialized_from_subquery` for `MATERIALIZED`), and are displayed when appropriate. There are no JSON equivalents for `SIMPLE` or `PRIMARY`.

当你用EXPLAIN指定FORMAT=JSON时，输出没有直接等效于select_type的单一属性;query_block属性对应于给定的SELECT。与刚才显示的大多数SELECT子查询类型等价的属性是可用的(materialized_from_subquery为MATERIALIZED提供了一个示例)，并在适当的时候显示。对于SIMPLE或PRIMARY，没有对应的JSON。

The select_type value for non-SELECT statements displays the statement type for affected tables. For example, select_type is DELETE for DELETE statements.

非select语句的select_type值显示受影响表的语句类型。例如，对于DELETE语句，select_type是DELETE。

###### table (JSON name: table_name)

The name of the table to which the row of output refers. This can also be one of the following values:

输出行所指向的表的名称。这也可以是以下值之一:

<unionM,N>: The row refers to the union of the rows with id values of M and N.

行指的是id值为M和N的行的并集。

<derivedN>: The row refers to the derived table result for the row with an id value of N. A derived table may result, for example, from a subquery in the FROM clause.

行引用id值为N的行的派生表结果。例如，派生表可能来自from子句中的子查询。

<subqueryN>: The row refers to the result of a materialized subquery for the row with an id value of N. See Section 8.2.2.2, “Optimizing Subqueries with Materialization”.

该行指的是id值为n的行的物化子查询的结果。参见8.2.2.2节“物化优化子查询”。

###### partitions (JSON name: partitions)

The partitions from which records would be matched by the query. The value is NULL for nonpartitioned tables. See Section 24.3.5, “Obtaining Information About Partitions”.

查询将与记录相匹配的分区。对于非分区表，该值为NULL。请参见24.3.5节“获取分区信息”。

###### type (JSON name: access_type)

The join type. For descriptions of the different types, see EXPLAIN Join Types.

连接类型。有关不同类型的描述，请参见解释连接类型。

###### possible_keys (JSON name: possible_keys)

The possible_keys column indicates the indexes from which MySQL can choose to find the rows in this table. Note that this column is totally independent of the order of the tables as displayed in the output from EXPLAIN. That means that some of the keys in possible_keys might not be usable in practice with the generated table order.

possible_keys列表示MySQL可以从其中选择查找该表中的行。注意，该列完全独立于EXPLAIN输出中显示的表的顺序。这意味着，在生成的表顺序中，possible_keys中的某些键可能不能实际使用。

If this column is NULL (or undefined in JSON-formatted output), there are no relevant indexes. In this case, you may be able to improve the performance of your query by examining the WHERE clause to check whether it refers to some column or columns that would be suitable for indexing. If so, create an appropriate index and check the query with EXPLAIN again. See Section 13.1.9, “ALTER TABLE Statement”.

如果该列为NULL(或者在json格式的输出中未定义)，则没有相关的索引。在这种情况下，您可以通过检查WHERE子句来检查它是否引用了一些或一些适合索引的列，从而提高查询的性能。如果是，创建一个适当的索引并再次使用EXPLAIN检查查询。参见章节13.1.9，“ALTER TABLE Statement”。

To see what indexes a table has, use SHOW INDEX FROM tbl_name.

###### key (JSON name: key)

The key column indicates the key (index) that MySQL actually decided to use. If MySQL decides to use one of the possible_keys indexes to look up rows, that index is listed as the key value.

键列表示MySQL实际决定使用的键(索引)。如果MySQL决定使用一个possible_keys索引来查找行，该索引将作为键值列出来。

It is possible that key may name an index that is not present in the possible_keys value. This can happen if none of the possible_keys indexes are suitable for looking up rows, but all the columns selected by the query are columns of some other index. That is, the named index covers the selected columns, so although it is not used to determine which rows to retrieve, an index scan is more efficient than a data row scan.

key可能会命名一个在possible_keys值中不存在的索引。如果没有一个 possible_keys索引适合查找行，但是查询选择的所有列都是其他某个索引的列，那么就会发生这种情况。也就是说，命名索引覆盖所选的列，因此尽管它不用于确定要检索哪些行，但索引扫描比数据行扫描更有效。

For InnoDB, a secondary index might cover the selected columns even if the query also selects the primary key because InnoDB stores the primary key value with each secondary index. If key is NULL, MySQL found no index to use for executing the query more efficiently.

对于InnoDB，即使查询同时选择了主键，一个二级索引也可能覆盖所选的列，因为InnoDB将主键值存储在每个二级索引中。如果key是NULL, MySQL发现没有索引可以更有效地执行查询。

To force MySQL to use or ignore an index listed in the possible_keys column, use FORCE INDEX, USE INDEX, or IGNORE INDEX in your query. See Section 8.9.4, “Index Hints”.

为了强制MySQL使用或忽略在possible_keys列中列出的索引，在你的查询中使用force index, use index，或ignore index。参见8.9.4节，“索引提示”。

For MyISAM tables, running ANALYZE TABLE helps the optimizer choose better indexes. For MyISAM tables, myisamchk --analyze does the same. See Section 13.7.3.1, “ANALYZE TABLE Statement”, and Section 7.6, “MyISAM Table Maintenance and Crash Recovery”.

对于MyISAM表，运行ANALYZE TABLE可以帮助优化器选择更好的索引。对于MyISAM表，myisamchk——analyze执行同样的操作。参见第13.7.3.1节“分析表语句”和第7.6节“MyISAM表维护和崩溃恢复”。

###### key_len (JSON name: key_length)

The key_len column indicates the length of the key that MySQL decided to use. The value of key_len enables you to determine how many parts of a multiple-part key MySQL actually uses. If the key column says NULL, the key_len column also says NULL.

key_len列表示MySQL决定使用的key的长度。key_len的值允许您确定MySQL实际使用的多部分key的多少部分。如果key列为NULL, key_len列也为NULL。

Due to the key storage format, the key length is one greater for a column that can be NULL than for a NOT NULL column.

由于键存储格式，可以为NULL的列的键长度比非NULL列的键长度大1。

###### ref (JSON name: ref)

The ref column shows which columns or constants are compared to the index named in the key column to select rows from the table.

ref列显示哪些列或常量与key列中命名的索引进行比较，以从表中选择行。

If the value is func, the value used is the result of some function. To see which function, use SHOW WARNINGS following EXPLAIN to see the extended EXPLAIN output. The function might actually be an operator such as an arithmetic operator.

如果值是func，则使用的值是某个函数的结果。要查看哪个函数，请使用EXPLAIN后面的SHOW WARNINGS查看扩展的EXPLAIN输出。这个函数实际上可能是一个运算符，比如算术运算符。

###### rows (JSON name: rows)

The rows column indicates the number of rows MySQL believes it must examine to execute the query.

rows列表示MySQL认为执行查询必须检查的行数。

For InnoDB tables, this number is an estimate, and may not always be exact.

对于InnoDB表，这个数字是一个估计，可能并不总是准确的。

###### filtered (JSON name: filtered)

The filtered column indicates an estimated percentage of table rows that are filtered by the table condition. The maximum value is 100, which means no filtering of rows occurred. Values decreasing from 100 indicate increasing amounts of filtering. rows shows the estimated number of rows examined and rows × filtered shows the number of rows that are joined with the following table. 

已筛选的列表示通过表条件筛选的表行的估计百分比。最大值是100，这意味着没有对行进行筛选。从100开始下降的值表明过滤的数量在增加。rows显示所检查的估计行数，rows×filtered显示与下表连接的行数。

For example, if rows is 1000 and filtered is 50.00 (50%), the number of rows to be joined with the following table is 1000 × 50% = 500.

例如，如果rows为1000，过滤后为50.00(50%)，则需要与下表连接的行数为1000×50% = 500。

###### Extra (JSON name: none)

This column contains additional information about how MySQL resolves the query. For descriptions of the different values, see EXPLAIN Extra Information.

本专栏包含关于MySQL如何解析查询的其他信息。有关不同值的描述，请参见解释额外信息。

There is no single JSON property corresponding to the Extra column; however, values that can occur in this column are exposed as JSON properties, or as the text of the message property.

没有一个JSON属性对应额外的列;但是，此列中可能出现的值将作为JSON属性或消息属性的文本公开。

##### EXPLAIN Join Types

The type column of EXPLAIN output describes how tables are joined. In JSON-formatted output, these are found as values of the access_type property. The following list describes the join types, ordered from the best type to the worst:

EXPLAIN输出的type列描述了如何连接表。在json格式的输出中，这些是access_type属性的值。下面的列表描述了连接类型，从最好的到最坏的排序:

###### system

The table has only one row (= system table). This is a special case of the const join type.

该表只有一行(=系统表)。这是const join类型的一个特殊情况。

###### const

The table has at most one matching row, which is read at the start of the query. Because there is only one row, values from the column in this row can be regarded as constants by the rest of the optimizer. const tables are very fast because they are read only once.

该表最多有一个匹配行，在查询开始时读取。因为只有一行，所以这一行中的列的值可以被优化器的其余部分视为常量。const表非常快，因为它们只被读取一次。

const is used when you compare all parts of a PRIMARY KEY or UNIQUE index to constant values. In the following queries, tbl_name can be used as a const table:

const用于比较主键或唯一索引的所有部分与常量值。在以下查询中，tbl_name可以用作const表:

```sql
SELECT * FROM tbl_name WHERE primary_key=1;
SELECT * FROM tbl_name WHERE primary_key_part1=1 AND primary_key_part2=2;
```

###### eq_ref

One row is read from this table for each combination of rows from the previous tables. Other than the system and const types, this is the best possible join type. It is used when all parts of an index are used by the join and the index is a PRIMARY KEY or UNIQUE NOT NULL index.

对于前面表中的每一行组合，从这个表中读取一行。与system和const类型不同，这是最好的连接类型。当一个索引的所有部分都被连接使用，并且索引是一个主键或唯一的NOT NULL索引时，就会使用它。

eq_ref can be used for indexed columns that are compared using the = operator. The comparison value can be a constant or an expression that uses columns from tables that are read before this table. In the following examples, MySQL can use an eq_ref join to process ref_table:

eq_ref可以用于使用=操作符进行比较的索引列。比较值可以是一个常量，也可以是使用在此表之前读取的表中的列的表达式。在下面的例子中，MySQL可以使用eq_ref join来处理ref_table:

```sql
SELECT * FROM ref_table,other_table
  WHERE ref_table.key_column=other_table.column;
SELECT * FROM ref_table,other_table WHERE ref_table.key_column_part1=other_table.column
  AND ref_table.key_column_part2=1;
```

###### ref

All rows with matching index values are read from this table for each combination of rows from the previous tables. ref is used if the join uses only a leftmost prefix of the key or if the key is not a PRIMARY KEY or UNIQUE index (in other words, if the join cannot select a single row based on the key value). If the key that is used matches only a few rows, this is a good join type.

对于前面表中的每个行组合，将从这个表中读取具有匹配索引值的所有行。如果连接仅使用key的最左边的`前缀`，或者键不是`主键`或`唯一索引`(换句话说，如果连接不能根据键值选择单行)，则使用ref。如果所使用的key只匹配几行，那么这是一种很好的连接类型.

ref can be used for indexed columns that are compared using the = or <=> operator. In the following examples, MySQL can use a ref join to process ref_table:

ref可以用于使用=或<=>操作符进行比较的索引列。在下面的例子中，MySQL可以使用一个ref join来处理ref_table:

```sql
SELECT * FROM ref_table WHERE key_column=expr;
SELECT * FROM ref_table,other_table
  WHERE ref_table.key_column=other_table.column;
SELECT * FROM ref_table,other_table
  WHERE ref_table.key_column_part1=other_table.column
  AND ref_table.key_column_part2=1;
```

###### fulltext

The join is performed using a FULLTEXT index.

连接使用FULLTEXT索引执行。

###### ref_or_null

This join type is like ref, but with the addition that MySQL does an extra search for rows that contain NULL values. This join type optimization is used most often in resolving subqueries. In the following examples, MySQL can use a ref_or_null join to process ref_table:

这种连接类型类似于ref，但是MySQL会对包含空值的行进行额外的搜索。这种连接类型优化最常用于解析子查询。在下面的例子中，MySQL可以使用ref_or_null连接来处理ref_table:

```sql
SELECT * FROM ref_table
  WHERE key_column=expr OR key_column IS NULL;
See Section 8.2.1.15, “IS NULL Optimization”.
```

###### index_merge

This join type indicates that the Index Merge optimization is used. In this case, the key column in the output row contains a list of indexes used, and key_len contains a list of the longest key parts for the indexes used. For more information, see Section 8.2.1.3, “Index Merge Optimization”.

这种连接类型表明使用了索引合并优化。在这种情况下，输出行中的键列包含使用的索引列表，而key_len包含所使用索引的最长键部分列表。更多信息，请参见8.2.1.3节“索引合并优化”。

###### unique_subquery

This type replaces eq_ref for some IN subqueries of the following form:

这种类型替换了以下形式的子查询中的一些eq_ref:

value IN (SELECT primary_key FROM single_table WHERE some_expr)
unique_subquery is just an index lookup function that replaces the subquery completely for better efficiency.

unique_subquery只是一个索引查找函数，它完全取代了子查询，以获得更好的效率。

###### index_subquery

This join type is similar to unique_subquery. It replaces IN subqueries, but it works for nonunique indexes in subqueries of the following form:

这种连接类型类似于unique_subquery。它在子查询中替换，但它适用于以下形式的子查询中的非唯一索引:

value IN (SELECT key_column FROM single_table WHERE some_expr)

###### range

Only rows that are in a given range are retrieved, using an index to select the rows. The key column in the output row indicates which index is used. The key_len contains the longest key part that was used. The ref column is NULL for this type.

只检索给定范围内的行，并使用索引来选择行。输出行中的键列指示使用了哪个索引。key_len包含所使用的最长的键部分。对于这种类型，ref列为NULL。

range can be used when a key column is compared to a constant using any of the =, <>, >, >=, <, <=, IS NULL, <=>, BETWEEN, LIKE, or IN() operators:

range可用于使用任意=、<>、>、>=、<、<=、NULL、<=>、BETWEEN、LIKE或IN()操作符将键列与常量进行比较:

```sql
SELECT * FROM tbl_name
  WHERE key_column = 10;

SELECT * FROM tbl_name
  WHERE key_column BETWEEN 10 and 20;

SELECT * FROM tbl_name
  WHERE key_column IN (10,20,30);

SELECT * FROM tbl_name
  WHERE key_part1 = 10 AND key_part2 IN (10,20,30);
```

###### index

The index join type is the same as ALL, except that the index tree is scanned. This occurs two ways:

除了索引树是扫描的。这有两种情况:

If the index is a covering index for the queries and can be used to satisfy all data required from the table, only the index tree is scanned. In this case, the Extra column says Using index. An index-only scan usually is faster than ALL because the size of the index usually is smaller than the table data.

如果索引是查询的覆盖索引，并且可以用来满足从表中需要的所有数据，那么只扫描索引树。在本例中， Extra column表示使用index。仅索引扫描通常比all扫描都快，因为索引的大小通常比表数据小。

A full table scan is performed using reads from the index to look up data rows in index order. Uses index does not appear in the Extra column.

使用从索引读取数据以按索引顺序查找数据行来执行全表扫描。使用索引不会出现在Extra 的列中。

MySQL can use this join type when the query uses only columns that are part of a single index.

当查询只使用属于单个索引的列时，MySQL可以使用这种连接类型。

###### ALL

A full table scan is done for each combination of rows from the previous tables. This is normally not good if the table is the first table not marked const, and usually very bad in all other cases. Normally, you can avoid ALL by adding indexes that enable row retrieval from the table based on constant values or column values from earlier tables.

对前面表中的每个行组合进行全表扫描。如果表是第一个没有标记为const的表，这通常不好，在其他所有情况下通常都很糟糕。通常，您可以通过添加索引来避免这一切，这些索引允许根据常值或早期表中的列值从表中检索行。