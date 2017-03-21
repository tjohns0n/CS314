<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
  <html>
  <body>
  <table width="90%" border="0">
    <tr bgcolor="blue">
      <th style="color:white">#</th>
      <th style="color:white">Origin</th>
      <th style="color:white">Destination</th>
      <th style="color:white">Mileage</th>
    </tr>
    <xsl:for-each select="trip/leg">
    <tr>
      <td><xsl:value-of select="sequence"/></td>
      <td><xsl:value-of select="start"/></td>
      <td><xsl:value-of select="finish"/></td>
      <td><xsl:value-of select="mileage"/></td>
    </tr>
    </xsl:for-each>
  </table>
  </body>
  </html>
</xsl:template>

</xsl:stylesheet>