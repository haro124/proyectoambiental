#!/usr/bin/env bash
#
# Convierte las fotos originales de sitiosturisticos/ a assets/sitios/<slug>/NN.jpg
#
# Las fotos originales son PNG de 2-8 MB cada una (~101 MB en total), demasiado
# grandes para empaquetar en el APK. Este script las reescala a 1280 px y las
# guarda como JPEG (~9 MB en total). La carpeta original NO se modifica.
#
# Requiere ImageMagick. Uso:  bash tools/convertir_fotos_sitios.sh
#
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
SRC="$ROOT/sitiosturisticos"
DST="$ROOT/app/src/main/assets/sitios"

mkdir -p "$DST"

convert_folder() {
  local folder="$1" slug="$2"
  local out="$DST/$slug"

  if [ ! -d "$SRC/$folder" ]; then
    echo "AVISO: no existe '$SRC/$folder', se omite"
    return
  fi

  rm -rf "$out"
  mkdir -p "$out"

  # La foto sin numero se toma como portada; el resto en orden natural.
  local files=()
  [ -f "$SRC/$folder/Imagen soltada.png" ] && files+=("$SRC/$folder/Imagen soltada.png")
  while IFS= read -r f; do
    files+=("$f")
  done < <(find "$SRC/$folder" -maxdepth 1 -name "Imagen soltada (*).png" | sort -V)

  local i=1
  for f in "${files[@]}"; do
    magick "$f" -auto-orient -resize '1280x1280>' -strip -quality 82 \
      "$out/$(printf '%02d' "$i").jpg"
    i=$((i + 1))
  done
  echo "$slug: $((i - 1)) fotos"
}

# carpeta original -> slug usado en Attraction.imageFolder (SampleData.kt)
convert_folder "ILA KUCHA"                     "ila_kucha"
convert_folder "ISHPINGO PAKCHA"               "ishpingo_pakcha"
convert_folder "Cavernas de Guami"             "cavernas_guami"
convert_folder "Complejo turístico el Cristal" "complejo_el_cristal"
