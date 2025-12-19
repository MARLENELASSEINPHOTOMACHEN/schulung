{{/*
Chart name
*/}}
{{- define "zeiterfassung.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Fullname
*/}}
{{- define "zeiterfassung.fullname" -}}
{{- if .Values.fullnameOverride }}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- .Release.Name | trunc 63 | trimSuffix "-" }}
{{- end }}
{{- end }}

{{/*
Common labels
*/}}
{{- define "zeiterfassung.labels" -}}
helm.sh/chart: {{ .Chart.Name }}-{{ .Chart.Version }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}

{{/*
Selector labels for zeiterfassung
*/}}
{{- define "zeiterfassung.selectorLabels" -}}
app: zeiterfassung
{{- end }}

{{/*
Selector labels for rabbitmq
*/}}
{{- define "rabbitmq.selectorLabels" -}}
app: rabbitmq
{{- end }}
