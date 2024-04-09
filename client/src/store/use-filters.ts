import { create } from 'zustand';

type FiltersStore = {
  systemMessages: boolean;
  setSystemMessages: (show: boolean) => void;
  highlightReported: boolean;
  setHighlightReported: (highlight: boolean) => void;
  highlightReporter: boolean;
  setHighlightReporter: (highlight: boolean) => void;
  reset: () => void;
};

export const useFilters = create<FiltersStore>((set) => ({
  systemMessages: true,
  setSystemMessages: (show) => set({ systemMessages: show }),
  highlightReported: false,
  setHighlightReported: (show) => set({ highlightReported: show }),
  highlightReporter: false,
  setHighlightReporter: (show) => set({ highlightReporter: show }),
  reset: () =>
    set({
      systemMessages: true,
      highlightReported: false,
      highlightReporter: false,
    }),
}));
