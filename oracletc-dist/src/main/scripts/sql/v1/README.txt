In order to generate XMLs files for each package, procedure or trigger:

1) Hosting schema must have DBMS_PROFILER tables populated previously (proftab.sql); 
2) There must be an Oracle directory pointing to E:\TestCoverage\XML must have been created; (create or replace directory TC as 'E:\TestCoverage\XML';)
3) UTL_FILE_DIR parameter must point to E:\TestCoverage\XML;
4) PKG_PROFILER must have been created (pkg_profiler.sql)
5) Run Oracle_TC.sql

For more details about test coverage, please refer to PLSQL_Profiling.docx
