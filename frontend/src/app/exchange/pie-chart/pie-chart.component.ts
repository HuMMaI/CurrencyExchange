import { Component, Input, OnChanges, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { ChartConfiguration, ChartData, ChartType } from 'chart.js';
import DataLabelsPlugin from 'chartjs-plugin-datalabels';
import { BaseChartDirective } from 'ng2-charts';

@Component({
  selector: 'app-pie-chart',
  templateUrl: './pie-chart.component.html',
  styleUrls: ['./pie-chart.component.scss']
})
export class PieChartComponent implements OnInit, OnChanges {
  @ViewChild(BaseChartDirective) chart?: BaseChartDirective;

  @Input() public labels?: string[];
  @Input() public data?: number[];
  @Input() public chartName?: string;

  public isReportExists = false;

  public pieChartOptions: ChartConfiguration['options'] = {
    responsive: true,
    plugins: {
      legend: {
        display: true,
        position: 'top',
      },
      datalabels: {
        formatter: (value, ctx) => {
          if (ctx.chart.data.labels) {
            return ctx.chart.data.labels[ctx.dataIndex];
          }
        },
      },
    }
  };
  public pieChartData: ChartData<'pie', number[], string | string[]> = {
    labels: [],
    datasets: [{
      data: []
    }]
  };
  public pieChartType: ChartType = 'pie';
  public pieChartPlugins = [DataLabelsPlugin];

  constructor() {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['labels']) {
      this.labelsInit();
    }

    if (changes['data']) {
      this.dataInit();
    }
  }

  ngOnInit(): void {
    this.labelsInit();
    this.dataInit();
  }

  private labelsInit(): void {
    if (this.labels?.length) {
      for (const label of this.labels) {
        this.pieChartData.labels?.push(label);
      }

      this.chart?.update();
    }
  }

  private dataInit(): void {
    if (this.data?.length) {
      for (const num of this.data) {
        this.pieChartData.datasets[0].data.push(num);
      }

      this.isReportExists = true;
      this.chart?.update();
    }
  }
}
