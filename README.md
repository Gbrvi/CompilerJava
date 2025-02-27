## 💻 About the project 
The project is to create a Compiler using **Java**. The code has a **Scanner** to analyse the tokens and the **Parser** check if it follows the grammar rules

## 🛠 How to execute
- **Scanner**: Change the Path of the file.txt
- **Parser**: Change the Path of the file.txt and execute the **Parse.java file**

## 📚 The grammar 
```bash
<program> → <declarations list>
<declarations list> → <declarations list> <declarations> |  
                        <declarations>
<declarations> → <variable declaration> |  
                  <function declaration>

<variable declaration> → <type> ident ; |  
                         <type> ident [ countint ];

<type> → int |  
          void

<function declaration> → <type> ident ( <formal parameters> ) <compound declaration>

<formal parameters> → <formal parameters list> | ε

<formal parameters list> → <parameter> , <formal parameters list> |  
                            <parameter>

<parameter> → <type> ident |  
               <type> ident [ ]

<compound declaration> → { <local declarations> <command list> }

<local declarations> → <local declarations> <variable declaration> | ε

<command list> → <command> <command list> | ε

<command> → <expression command> |  
             <compound command> |  
             <selection command> |  
             <iteration command> |  
             <return command>

<expression command> → <expression> ; |

<iteration command> → while (<expression>) <command>

<selection command> → if (<expression>) <command> |  
                       if (<expression>) <command> else <command>

<return command> → return; |  
                    return <expression> ;

<compound command> → { <command list> }

<expression> → <var> = <expression> |  
                <simple expression>

<var> → ident |  
        ident [ <expression> ]

<simple expression> → <sum expressions> <relational operator> <sum expressions> | <sum expressions>

<relational operator> → > | < | <= | >= | == | !=

<sum expressions> → <sum expressions> <additive operator> <term> | <term>

<additive operator> → + | -

<term> → <term> <multiplicative operator> <factor> | <factor>

<multiplicative operator> → * | /

<factor> → ( <expression> ) | <var> | <activation> | contint

<activation> → ident ( <args> )

<args> → <args-list> | ε

<args-list> → <args-list> , <expression> | <expression>
````

