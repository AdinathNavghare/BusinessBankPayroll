AmCharts.AmPieChart = AmCharts.Class({
		inherits : AmCharts.AmSlicedChart,
		construct : function () {
			AmCharts.AmPieChart.base.construct.call(this);
			this.pieBrightnessStep = 30;
			this.minRadius = 10;
			this.depth3D = 0;
			this.startAngle = 90;
			this.angle = this.innerRadius = 0;
			this.startRadius = "500%";
			this.pullOutRadius = "20%";
			this.labelRadius = 30;
			this.labelText = "[[title]]: [[percents]]%";
			this.balloonText = "[[title]]: [[percents]]% ([[value]])\n[[description]]";
			this.previousScale = 1
		},
		drawChart : function () {
			AmCharts.AmPieChart.base.drawChart.call(this);
			var e = this.chartData;
			if (AmCharts.ifArray(e)) {
				if (0 < this.realWidth && 0 < this.realHeight) {
					AmCharts.VML && (this.startAlpha = 1);
					var g = this.startDuration,
					c = this.container,
					a = this.updateWidth();
					this.realWidth = a;
					var h = this.updateHeight();
					this.realHeight = h;
					var d = AmCharts.toCoordinate,
					k = d(this.marginLeft, a),
					b = d(this.marginRight, a),
					q = d(this.marginTop, h) + this.getTitleHeight(),
					l = d(this.marginBottom, h),
					w,
					x,
					f,
					s = AmCharts.toNumber(this.labelRadius),
					r = this.measureMaxLabel();
					this.labelText && this.labelsEnabled || (s = r = 0);
					w =
						void 0 === this.pieX ? (a - k - b) / 2 + k : d(this.pieX, this.realWidth);
					x = void 0 === this.pieY ? (h - q - l) / 2 + q : d(this.pieY, h);
					f = d(this.radius, a, h);
					f || (a = 0 <= s ? a - k - b - 2 * r : a - k - b, h = h - q - l, f = Math.min(a, h), h < a && (f /= 1 - this.angle / 90, f > a && (f = a)), h = AmCharts.toCoordinate(this.pullOutRadius, f), f = (0 <= s ? f - 1.8 * (s + h) : f - 1.8 * h) / 2);
					f < this.minRadius && (f = this.minRadius);
					h = d(this.pullOutRadius, f);
					q = AmCharts.toCoordinate(this.startRadius, f);
					d = d(this.innerRadius, f);
					d >= f && (d = f - 1);
					l = AmCharts.fitToBounds(this.startAngle, 0, 360);
					0 < this.depth3D &&
					(l = 270 <= l ? 270 : 90);
					l -= 90;
					a = f - f * this.angle / 90;
					for (k = 0; k < e.length; k++)
						if (b = e[k], !0 !== b.hidden && 0 < b.percents) {
							var n = 360 * b.percents / 100,
							r = Math.sin((l + n / 2) / 180 * Math.PI),
							y = -Math.cos((l + n / 2) / 180 * Math.PI) * (a / f),
							p = {
								fill : b.color,
								stroke : this.outlineColor,
								"stroke-width" : this.outlineThickness,
								"stroke-opacity" : this.outlineAlpha
							};
							b.url && (p.cursor = "pointer");
							p = AmCharts.wedge(c, w, x, l, n, f, a, d, this.depth3D, p, this.gradientRatio, b.pattern);
							this.addEventListeners(p, b);
							b.startAngle = l;
							e[k].wedge = p;
							if (0 < g) {
								var t = this.startAlpha;
								this.chartCreated && (t = b.alpha);
								p.setAttr("opacity", t)
							}
							b.ix = r;
							b.iy = y;
							b.wedge = p;
							b.index = k;
							if (this.labelsEnabled && this.labelText && b.percents >= this.hideLabelsPercent) {
								var m = l + n / 2,
								n = s;
								isNaN(b.labelRadius) || (n = b.labelRadius);
								var t = w + r * (f + n),
								u = x + y * (f + n),
								z,
								v = 0;
								if (0 <= n) {
									var A;
									90 >= m && 0 <= m ? (A = 0, z = "start", v = 8) : 90 <= m && 180 > m ? (A = 1, z = "start", v = 8) : 180 <= m && 270 > m ? (A = 2, z = "end", v = -8) : 270 <= m && 360 > m && (A = 3, z = "end", v = -8);
									b.labelQuarter = A
								} else
									z = "middle";
								var m = this.formatString(this.labelText, b),
								B = b.labelColor;
								B || (B = this.color);
								m = AmCharts.text(c, m, B, this.fontFamily, this.fontSize, z);
								m.translate(t + 1.5 * v, u);
								b.tx = t + 1.5 * v;
								b.ty = u;
								u = d + (f - d) / 2;
								b.pulled && (u += this.pullOutRadiusReal);
								b.balloonX = r * u + w;
								b.balloonY = y * u + x;
								0 <= n ? p.push(m) : this.freeLabelsSet.push(m);
								b.label = m;
								b.tx = t;
								b.tx2 = t + v;
								b.tx0 = w + r * f;
								b.ty0 = x + y * f
							}
							b.startX = Math.round(r * q);
							b.startY = Math.round(y * q);
							b.pullX = Math.round(r * h);
							b.pullY = Math.round(y * h);
							this.graphsSet.push(p);
							(0 === b.alpha || 0 < g && !this.chartCreated) && p.hide();
							l += 360 * b.percents / 100
						}
					0 < s && !this.labelRadiusField && this.arrangeLabels();
					this.pieXReal = w;
					this.pieYReal = x;
					this.radiusReal = f;
					this.innerRadiusReal = d;
					0 < s && this.drawTicks();
					this.initialStart();
					this.setDepths()
				}
				(e = this.legend) && e.invalidateSize()
			} else
				this.cleanChart();
			this.dispDUpd();
			this.chartCreated = !0
		},
		setDepths : function () {
			var e = this.chartData,
			g;
			for (g = 0; g < e.length; g++) {
				var c = e[g],
				a = c.wedge,
				c = c.startAngle;
				0 <= c && 180 > c ? a.toFront() : 180 <= c && a.toBack()
			}
		},
		arrangeLabels : function () {
			var e = this.chartData,
			g = e.length,
			c,
			a;
			for (a = g - 1; 0 <= a; a--)
				c = e[a], 0 !== c.labelQuarter || c.hidden || this.checkOverlapping(a,
					c, 0, !0, 0);
			for (a = 0; a < g; a++)
				c = e[a], 1 != c.labelQuarter || c.hidden || this.checkOverlapping(a, c, 1, !1, 0);
			for (a = g - 1; 0 <= a; a--)
				c = e[a], 2 != c.labelQuarter || c.hidden || this.checkOverlapping(a, c, 2, !0, 0);
			for (a = 0; a < g; a++)
				c = e[a], 3 != c.labelQuarter || c.hidden || this.checkOverlapping(a, c, 3, !1, 0)
		},
		checkOverlapping : function (e, g, c, a, h) {
			var d,
			k,
			b = this.chartData,
			q = b.length,
			l = g.label;
			if (l) {
				if (!0 === a)
					for (k = e + 1; k < q; k++)
						b[k].labelQuarter == c && (d = this.checkOverlappingReal(g, b[k], c)) && (k = q);
				else
					for (k = e - 1; 0 <= k; k--)
						b[k].labelQuarter ==
						c && (d = this.checkOverlappingReal(g, b[k], c)) && (k = 0);
				!0 === d && 100 > h && (d = g.ty + 3 * g.iy, g.ty = d, l.translate(g.tx2, d), this.checkOverlapping(e, g, c, a, h + 1))
			}
		},
		checkOverlappingReal : function (e, g, c) {
			var a = !1,
			h = e.label,
			d = g.label;
			e.labelQuarter != c || e.hidden || g.hidden || !d || (h = h.getBBox(), c = {}, c.width = h.width, c.height = h.height, c.y = e.ty, c.x = e.tx, e = d.getBBox(), d = {}, d.width = e.width, d.height = e.height, d.y = g.ty, d.x = g.tx, AmCharts.hitTest(c, d) && (a = !0));
			return a
		}
	});