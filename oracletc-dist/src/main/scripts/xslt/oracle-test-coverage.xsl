<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns:xdt="http://www.w3.org/2005/xpath-datatypes">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	
	<xsl:strip-space elements="LINE TOTAL_OCCUR"/>

	<xsl:template match="ROWSET">
		<xsl:variable name="packageLine" select="LINE[contains(string(TEXT), 'PACKAGE')]/TEXT"/>
		<xsl:variable name="packageStart" select="substring-after($packageLine, 'PACKAGE BODY ')"/>
		<xsl:variable name="packageName" select="substring($packageStart, 1, string-length($packageStart)-3)"/>
		
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>Oracle Test Coverage Report</title>
<link title="Style" type="text/css" rel="stylesheet" href="./css/main.css"/>
</head>
<body>
<h5>Oracle Test Coverage Report - <xsl:value-of select="$packageName"/></h5>
<div class="separator">&#160;</div>
<table class="report">
<thead><tr>  <td class="heading">Procedures in this File</td>  <td class="heading">Line Coverage</td>  </tr></thead>

	<!-- PROCEDURES -->
	<xsl:for-each select="LINE[contains(string(TEXT), 'PROCEDURE') or contains(string(TEXT), 'FUNCTION') or contains(string(TEXT), 'TRIGGER')]">
		<xsl:variable name="procedureLine" select="TEXT"/>
		<xsl:variable name="procedureStart" select="concat(substring-after($procedureLine, 'PROCEDURE '), substring-after($procedureLine, 'FUNCTION '), substring-after($procedureLine, 'TRIGGER '))"/>
		<xsl:variable name="procedureName" select="substring-before(translate(concat($procedureStart, '('), '(', ' '), ' ')"/>

		<xsl:variable name="startLineNumber" select="LINE"/>
		<xsl:variable name="endLineNumber" select="/ROWSET/LINE[contains(string(TEXT), 'END;') and LINE>$startLineNumber][1]/LINE"/>

		<xsl:variable name="totalCovered" select="count(/ROWSET/LINE[LINE > $startLineNumber and LINE &lt; $endLineNumber and TOTAL_OCCUR>0])"/>
		<xsl:variable name="totalUncovered" select="count(/ROWSET/LINE[LINE > $startLineNumber and LINE &lt; $endLineNumber and 0>=TOTAL_OCCUR])"/>
		<xsl:variable name="totalSum" select="$totalCovered+$totalUncovered"/>
		<xsl:variable name="totalPercentage" select="round(($totalCovered div $totalSum)*100)"/>
	<tr>
	  <td><a>
			<xsl:attribute name="href">
				<xsl:text>#<xsl:value-of select="$procedureName"/></xsl:text>
			</xsl:attribute>
		  <xsl:value-of select="$procedureName"/></a>
	  </td>
	  <td><table cellpadding="0px" cellspacing="0px" class="percentgraph"><tr class="percentgraph"><td align="right" class="percentgraph" width="40"><xsl:value-of select="$totalPercentage"/>%</td><td class="percentgraph"><div class="percentgraph"><div class="greenbar">
			<xsl:attribute name="style">
				<xsl:text><xsl:value-of select="concat('width:', string($totalPercentage), 'px')"/></xsl:text>
			</xsl:attribute>
	  <span class="text"><xsl:value-of select="$totalCovered"/>/<xsl:value-of select="$totalSum"/></span></div></div></td></tr></table></td>
	</tr>
	</xsl:for-each>

	<!-- TOTAL -->
	<xsl:variable name="totalCovered" select="count(LINE[not(contains(string(TEXT), 'PROCEDURE')) and not(contains(string(TEXT), 'FUNCTION')) and not(contains(string(TEXT), 'TRIGGER')) and not(contains(string(TEXT), 'PACKAGE')) and not(contains(string(TEXT), 'END;')) and TOTAL_OCCUR>0])"/>
	<xsl:variable name="totalUncovered" select="count(LINE[not(contains(string(TEXT), 'PROCEDURE')) and not(contains(string(TEXT), 'FUNCTION')) and not(contains(string(TEXT), 'TRIGGER')) and not(contains(string(TEXT), 'PACKAGE')) and not(contains(string(TEXT), 'END;')) and 0>=TOTAL_OCCUR])"/>
	<xsl:variable name="totalSum" select="$totalCovered+$totalUncovered"/>
	<xsl:variable name="totalPercentage" select="round(($totalCovered div $totalSum)*100)"/>
	<tr class="total">
	  <td class="title">TOTAL</td>
	  <td><table cellpadding="0px" cellspacing="0px" class="percentgraph"><tr class="percentgraph"><td align="right" class="percentgraph" width="40"><xsl:value-of select="$totalPercentage"/>%</td><td class="percentgraph"><div class="percentgraph"><div class="greenbar">
			<xsl:attribute name="style">
				<xsl:text><xsl:value-of select="concat('width:', string($totalPercentage), 'px')"/></xsl:text>
			</xsl:attribute>
	  <span class="text"><xsl:value-of select="$totalCovered"/>/<xsl:value-of select="$totalSum"/></span></div></div></td></tr></table></td>
	</tr>

</table>
<div class="separator">&#160;</div>
<div class="footer">Columns in this table: 
	<ul>
		<li>Line number</li>
		<li>Number of times executed</li>
		<li>Total execution time (ms)</li>
		<li>Source code</li>
	</ul>
</div>
<div class="separator">&#160;</div>
<table cellspacing="0" cellpadding="0" class="src">

     <xsl:apply-templates/>

</table>

<div class="footer">Report auto-generated on <xsl:value-of select="'TODO'"/></div>
</body>
</html>
	</xsl:template>


	<!--
		* TEMPLATE FOR DISPLAYING ALL LINES OF SOURCE CODE
	-->
	<xsl:template name="showSource" match="ROWSET/LINE">
		<xsl:variable name="isDeclaration" select="contains(string(TEXT), 'PROCEDURE') or contains(string(TEXT), 'FUNCTION') or contains(string(TEXT), 'TRIGGER') or contains(string(TEXT), 'PACKAGE') or contains(string(TEXT), 'END;')"/>
		<xsl:variable name="isCovered" select="not($isDeclaration) and TOTAL_OCCUR>0"/>
		<xsl:variable name="isUncovered" select="not($isDeclaration) and 0>=TOTAL_OCCUR"/>
	<tr>
		<td class="numLine" nowrap="nowrap">
			 <xsl:if test="$isUncovered">
				<xsl:attribute name="class">
					<xsl:text>nbHitsUncovered</xsl:text>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="$isCovered">
				<xsl:attribute name="class">
					<xsl:text>nbHitsCovered</xsl:text>
				</xsl:attribute>
			</xsl:if>
			&#160;<xsl:value-of select="LINE"/>
		</td>
		<td class="nbHits" nowrap="nowrap">
			<xsl:if test="$isUncovered">
				<xsl:attribute name="class">
					<xsl:text>nbHitsUncovered</xsl:text>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="$isCovered">
				<xsl:attribute name="class">
					<xsl:text>nbHitsCovered</xsl:text>
				</xsl:attribute>
			</xsl:if>
			&#160;<xsl:value-of select="TOTAL_OCCUR"/>
		</td>
		<td class="nbHits" nowrap="nowrap">
			<xsl:if test="$isUncovered">
				<xsl:attribute name="class">
					<xsl:text>nbHitsUncovered</xsl:text>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="$isCovered">
				<xsl:attribute name="class">
					<xsl:text>nbHitsCovered</xsl:text>
				</xsl:attribute>
			</xsl:if>
			&#160;<xsl:value-of select="TOTAL_TIME"/>
		</td>
		<td class="src" nowrap="nowrap">
			   <xsl:choose>
				   <xsl:when test="$isUncovered">
						<pre class="srcUncovered">&#160;<xsl:value-of select="TEXT"/></pre>
				   </xsl:when>
				   <xsl:when test="$isCovered">
						<pre class="srcCovered">&#160;<xsl:value-of select="TEXT"/></pre>
				   </xsl:when>
				   <xsl:when test="$isDeclaration">
					   <xsl:choose>
									<xsl:when test="contains(string(TEXT), 'PROCEDURE') or contains(string(TEXT), 'FUNCTION') or contains(string(TEXT), 'TRIGGER')">
										<xsl:variable name="procedureLine" select="TEXT"/>
										<xsl:variable name="procedureStart" select="concat(substring-after($procedureLine, 'PROCEDURE '), substring-after($procedureLine, 'FUNCTION '), substring-after($procedureLine, 'TRIGGER '))"/>
										<xsl:variable name="procedureName" select="substring-before(translate(concat($procedureStart, '('), '(', ' '), ' ')"/>
										<a>
										<xsl:attribute name="name">
											<xsl:text><xsl:value-of select="$procedureName"/></xsl:text>
										</xsl:attribute>
										<pre class="srcDeclaration">&#160;<xsl:value-of select="TEXT"/></pre>
										</a>
									</xsl:when>
									<xsl:otherwise>
										<pre class="srcDeclaration">&#160;<xsl:value-of select="TEXT"/></pre>
									</xsl:otherwise>
					   </xsl:choose>
				   </xsl:when>
				   <xsl:otherwise>
						<pre class="src">&#160;<xsl:value-of select="TEXT"/></pre>
				   </xsl:otherwise>
			   </xsl:choose>
		</td>
	</tr>
	</xsl:template>

</xsl:stylesheet>
